package dku.capstone.foodlog.domain;

import dku.capstone.foodlog.constant.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date birthday;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Subscribe> subscribers = new ArrayList<>();

    private String profilePicture;

    private String selfBio;

}
