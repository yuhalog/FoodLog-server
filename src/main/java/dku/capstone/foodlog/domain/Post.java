package dku.capstone.foodlog.domain;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.constant.FoodType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<PostPicture> pictureList = new ArrayList<>();

    private int rating;

    private String review;

    @Enumerated(EnumType.STRING)
    private FoodType type;

    @Enumerated(EnumType.STRING)
    private FoodPurpose purpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(Member member, List<PostPicture> pictureList, Integer rating, String review,
                FoodType type, FoodPurpose purpose, Place place){
        this.member = member;
        this.pictureList = pictureList;
        this.rating = rating;
        this.review = review;
        this.type = type;
        this.purpose = purpose;
        this.place = place;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
