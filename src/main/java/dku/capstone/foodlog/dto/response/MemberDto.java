package dku.capstone.foodlog.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dku.capstone.foodlog.constant.Gender;
import dku.capstone.foodlog.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class MemberDto {

    private Long memberId;

    private String email;

    private String username;

    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthday;

    private String selfBio;

    private String profilePicture;

    private boolean isFollowing;

    public MemberDto(Member member, boolean isFollowing) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.gender = member.getGender();
        this.birthday = member.getBirthday();
        this.selfBio = member.getSelfBio();
        this.profilePicture = member.getProfilePicture();
        this.isFollowing = isFollowing;
    }
}
