package dku.capstone.foodlog.api;

import dku.capstone.foodlog.dto.response.GoogleUserDto;
import dku.capstone.foodlog.dto.response.LoginResponse;
import dku.capstone.foodlog.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OAuthApiController {

    private final OAuthService oAuthService;

    @GetMapping("/google/login")
    public void googleLoginRedirectRequest() throws IOException {
        oAuthService.request();
    }

    @GetMapping("/google/login/redirect")
    public ResponseEntity<LoginResponse> googleLoginRedirect(
            @RequestParam(name = "code") String code) throws IOException {

        GoogleUserDto getGoogleUserDto = oAuthService.oAuthLogin(code);
        String email = getGoogleUserDto.getEmail();
        LoginResponse response = oAuthService.loginByGoogle(email);
        log.info("ACCESS-TOKEN :" + response.getAccessToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
