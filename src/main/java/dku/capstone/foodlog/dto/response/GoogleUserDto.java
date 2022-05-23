package dku.capstone.foodlog.dto.response;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class GoogleUserDto {

    private String id;
    private String email;
    private String name;
}
