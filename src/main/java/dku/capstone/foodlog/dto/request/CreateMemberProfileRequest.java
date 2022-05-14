package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.Gender;
import dku.capstone.foodlog.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class CreateMemberProfileRequest {

    private String username;
    private Gender gender;
    private Date birthday;
    private String profilePicture;
    private String selfBio;

    @Builder
    public CreateMemberProfileRequest(String username, Gender gender, Date birthday, String profilePicture, String selfBio) {
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.profilePicture = profilePicture;
        this.selfBio = selfBio;
    }

    public Member toMemberEntity() {
        return Member.builder()
                .username(username)
                .gender(gender)
                .birthday(birthday)
                .profilePicture(profilePicture)
                .selfBio(selfBio)
                .build();
    }
}
