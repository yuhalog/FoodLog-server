package dku.capstone.foodlog.dto.response;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class GoogleUserDto {

    private String id;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
}
