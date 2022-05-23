package dku.capstone.foodlog.dto.response;

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
    private LocalDate birthday;
    private String selfBio;
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
