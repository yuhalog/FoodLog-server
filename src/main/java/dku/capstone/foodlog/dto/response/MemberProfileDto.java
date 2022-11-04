package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberProfileDto {

    private String username;
    private Gender gender;
    private String birthday;
    private String selfBio;
    private String profilePicture;
}
