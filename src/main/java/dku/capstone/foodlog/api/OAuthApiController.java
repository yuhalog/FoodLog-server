package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.response.GoogleUserDto;
import dku.capstone.foodlog.service.OAuthService;
import dku.capstone.foodlog.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OAuthApiController {

    private final OAuthService oAuthService;
    private final JwtUtils jwtUtils;

    @GetMapping("/google/login")
    public void googleLoginRedirectRequest() throws IOException {
        oAuthService.request();
    }

    @GetMapping("/google/login/redirect")
    public void googleLoginRedirect(
            @RequestParam(name = "code") String code,
            HttpServletResponse response) throws IOException {

        GoogleUserDto getGoogleUserDto = oAuthService.oAuthLogin(code);

        String email = getGoogleUserDto.getEmail();
        Member findMemberByEmail = oAuthService.findOneByEmail(email);

        if (findMemberByEmail!=null) {
            // 로그인
            Long memberId = findMemberByEmail.getId();
            String jwt = jwtUtils.createJwtToken(memberId);
            log.info("login member jwt : " + jwt);

            String redirect_uri = "/api/map";
            response.sendRedirect(redirect_uri);

        } else {
            // 회원가입
            Member member = Member.builder()
                    .email(email)
                    .build();
            Long memberId = oAuthService.join(member);
            String jwt = jwtUtils.createJwtToken(memberId);
            log.info("join member jwt : " + jwt);

            String redirect_uri = "/api/member/create/";
            response.sendRedirect(redirect_uri+memberId);
        }
    }
}
