package dku.capstone.foodlog.dto.request;

import dku.capstone.foodlog.constant.Gender;
import lombok.Data;

@Data
public class MemberJoinRequest {

    private String email;

    private String username;

    private Gender gender;

    private String birthday;

    private String selfBio;

    private String profilePicture;

}
