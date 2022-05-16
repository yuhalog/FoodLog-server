package dku.capstone.foodlog.domain;

import dku.capstone.foodlog.constant.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicInsert
public class Member extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Subscribe> subscribers = new ArrayList<>();

    @ColumnDefault(value = "'default image'")
    private String profilePicture;

    private String selfBio;


    @Builder
    public Member(Long id, String email, String username, Gender gender, LocalDate birthday, String profilePicture, String selfBio) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.profilePicture = profilePicture;
        this.selfBio = selfBio;
    }

    public void createMemberProfile(String username, Gender gender, LocalDate birthday, String profilePicture, String selfBio) {
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.profilePicture = profilePicture;
        this.selfBio = selfBio;
    }
}
