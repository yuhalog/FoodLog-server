package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.GoogleOAuthToken;
import dku.capstone.foodlog.dto.response.GoogleUserDto;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.utils.GoogleUtils;
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
    private final HttpServletResponse response;
    private final MemberRepository memberRepository;

    public void request() throws IOException {

        String redirectURL = googleUtils.googleRedirectUrl();
        response.sendRedirect(redirectURL);
    }


    public GoogleUserDto oAuthLogin(String code) throws IOException {

        ResponseEntity<String> accessTokenResponse = googleUtils.requestAccessToken(code);
        GoogleOAuthToken googleOAuthToken = googleUtils.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleUtils.requestUserInfo(googleOAuthToken);
        GoogleUserDto googleUserDto = googleUtils.getUserInfo(userInfoResponse);

        return googleUserDto;
    }

    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

}