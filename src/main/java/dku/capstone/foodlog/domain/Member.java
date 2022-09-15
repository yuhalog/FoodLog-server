package dku.capstone.foodlog.domain;

import dku.capstone.foodlog.constant.Gender;
import dku.capstone.foodlog.dto.response.MemberProfileDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
public class Member extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<Post>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Subscribe> subscribers = new ArrayList<Subscribe>();

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

    public void updateProfile(MemberProfileDto request) {
        this.username = request.getUsername();
        this.gender = request.getGender();
        this.birthday = request.getBirthday();
        this.selfBio = request.getSelfBio();
        this.profilePicture = request.getProfilePicture();
    }
}
