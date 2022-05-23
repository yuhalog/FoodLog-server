package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.GoogleOAuthToken;
import dku.capstone.foodlog.dto.response.GoogleUserDto;
import dku.capstone.foodlog.dto.response.LoginResponse;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.utils.GoogleUtils;
import dku.capstone.foodlog.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OAuthService {

    private final GoogleUtils googleUtils;
    private final JwtUtils jwtUtils;
    private final HttpServletResponse httpServletResponse;
    private final MemberRepository memberRepository;

    public void request() throws IOException {

        String redirectURL = googleUtils.googleRedirectUrl();
        httpServletResponse.sendRedirect(redirectURL);
    }


    public GoogleUserDto oAuthLogin(String code) throws IOException {

        ResponseEntity<String> accessTokenResponse = googleUtils.requestAccessToken(code);
        GoogleOAuthToken googleOAuthToken = googleUtils.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleUtils.requestUserInfo(googleOAuthToken);
        GoogleUserDto googleUserDto = googleUtils.getUserInfo(userInfoResponse);

        return googleUserDto;
    }

    public LoginResponse loginByGoogle(String email) {

        Member findMemberByEmail = memberRepository.findByEmail(email);

        if (findMemberByEmail != null) {
            return login(findMemberByEmail);
        }
        return join(email);
    }

    @Transactional
    public LoginResponse join(String email) {
        Member findMemberByEmail = memberRepository.findByEmail(email);

        try {
            if (findMemberByEmail == null) {
                Member member = Member.builder()
                        .email(email)
                        .build();

                memberRepository.save(member);
                String token = jwtUtils.createToken(email, member.getId());

                return new LoginResponse(member.getId(), token, false);
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public LoginResponse login(Member member) {
        Long memberId = member.getId();
        String token = jwtUtils.createToken(member.getEmail(), memberId);

        return new LoginResponse(memberId, token, true);
    }
}