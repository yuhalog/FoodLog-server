package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberPageResponse {

    private Long id;
    private Long memberId;
    private String username;
    private String profilePicture;

    public MemberPageResponse(Subscribe subscribe) {
        this.id = subscribe.getId();
        this.memberId = subscribe.getSubscriber().getId();
        this.username = subscribe.getSubscriber().getUsername();
        this.profilePicture = subscribe.getSubscriber().getProfilePicture();
    }

    public static MemberPageResponse FollowerResponse(Subscribe subscribe) {
        return MemberPageResponse.builder()
                .id(subscribe.getId())
                .memberId(subscribe.getMember().getId())
                .username(subscribe.getMember().getUsername())
                .profilePicture(subscribe.getMember().getUsername())
                .build();
    }

    public MemberPageResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.profilePicture = member.getProfilePicture();
    }
}
