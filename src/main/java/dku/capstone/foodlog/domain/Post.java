package dku.capstone.foodlog.domain;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.constant.FoodType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class Post extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<PostPicture> pictureList = new ArrayList<>();

    private Float rating;

    private String review;

    @Enumerated(EnumType.STRING)
    private FoodType type;

    @Enumerated(EnumType.STRING)
    private FoodPurpose purpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private Date date;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

}
