package dku.capstone.foodlog.domain;

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

    private Gender Gender;

    private Date birthday;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Subscribe> subscribers = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture profilePic;

    private String selfBio;

}
