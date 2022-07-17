package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberProfileDto {

    private String username;
    private Gender gender;
    private LocalDate birthday;
    private String selfBio;
    private String profilePicture;
}
