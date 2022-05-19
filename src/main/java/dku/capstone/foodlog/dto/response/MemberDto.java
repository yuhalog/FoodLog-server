package dku.capstone.foodlog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dku.capstone.foodlog.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Data
public class MemberDto {

    @JsonProperty(value = "member_id")
    private Long memberId;
    private String email;
    private String username;
    private LocalDate birthday;

    @JsonProperty(value = "self_bio")
    private String selfBio;

    @JsonProperty(value = "profile_picture")
    private String profilePicture;

    public MemberDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.birthday = member.getBirthday();
        this.selfBio = member.getSelfBio();
        this.profilePicture = member.getProfilePicture();
    }
}
