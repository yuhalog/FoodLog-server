package dku.capstone.foodlog.dto.response;

import dku.capstone.foodlog.domain.Subscribe;
import lombok.Data;

@Data
public class MemberPageResponse {

    private Long id;
    private String username;
    private String profilePicture;

    public MemberPageResponse(Subscribe subscribe) {
        this.id = subscribe.getId();
        this.username = subscribe.getSubscriber().getUsername();
        this.profilePicture = subscribe.getSubscriber().getProfilePicture();
    }
}
