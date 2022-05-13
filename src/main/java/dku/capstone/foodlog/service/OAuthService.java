package dku.capstone.foodlog.service;

import dku.capstone.foodlog.dto.request.GoogleOAuthToken;
import dku.capstone.foodlog.dto.response.GoogleUserDto;
import dku.capstone.foodlog.utils.GoogleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class OAuthService {

    private final GoogleUtils googleUtils;
    private final HttpServletResponse response;

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
}