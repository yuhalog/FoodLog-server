package dku.capstone.foodlog.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class GoogleOAuthToken {

    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
