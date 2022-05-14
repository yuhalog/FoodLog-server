package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.response.GoogleUserDto;
import dku.capstone.foodlog.dto.response.MemberDto;
import dku.capstone.foodlog.service.OAuthService;
import dku.capstone.foodlog.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OAuthApiController {

    private final OAuthService oAuthService;
    private final JwtUtils jwtUtils;

    @ApiOperation(value = "", notes = "구글 로그인 후 리다이렉트 url 요청")
    @GetMapping("/google/login")
    public void googleLoginRedirect() throws IOException {
        oAuthService.request();
    }

    @ApiOperation(value = "", notes = "DB와 비교해서 로그인 혹은 회원가입 후에 jwt 발급")
    @GetMapping("/google/login/redirect")
    public MemberDto callback(
            @RequestParam(name = "code") String code) throws IOException {
        GoogleUserDto getGoogleUserDto = oAuthService.oAuthLogin(code);

        String email = getGoogleUserDto.getEmail();
        Member findMemberByEmail = oAuthService.findOneByEmail(email);

        if (findMemberByEmail!=null) {
            // 로그인
            Long memberId = findMemberByEmail.getId();
            String jwtToken = jwtUtils.createJwtToken(memberId);
            MemberDto memberDto = new MemberDto(jwtToken, memberId, email);

            return memberDto;

        } else {
            // 회원가입
            Member member = new Member(email);
            Long memberId = oAuthService.join(member);
            String jwtToken = jwtUtils.createJwtToken(memberId);
            MemberDto memberDto = new MemberDto(jwtToken, memberId, email);

            return memberDto;
        }
    }
}
