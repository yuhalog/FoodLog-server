package dku.capstone.foodlog.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class GoogleOAuthToken {

    private String accessToken;
    private String expiresIn;
    private String scope;
    private String tokenType;
    private String idToken;
}
