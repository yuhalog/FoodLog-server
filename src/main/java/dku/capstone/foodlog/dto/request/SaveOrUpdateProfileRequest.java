package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveOrUpdateProfileRequest {

    private String username;
    private Gender gender;
    private LocalDate birthday;
    private String profilePicture;
    private String selfBio;
}
