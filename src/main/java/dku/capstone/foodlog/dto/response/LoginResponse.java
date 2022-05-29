package dku.capstone.foodlog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {

    private Long id;
    private String accessToken;
    private boolean isMember;
}
