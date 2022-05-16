package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateMemberProfileRequest {

    private String username;
    private Gender gender;
    private LocalDate birthday;
    private String profilePicture;
    private String selfBio;

    @Builder
    public CreateMemberProfileRequest(String username, Gender gender, LocalDate birthday, String profilePicture, String selfBio) {
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.profilePicture = profilePicture;
        this.selfBio = selfBio;
    }
}
