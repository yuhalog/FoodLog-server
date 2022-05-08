package dku.capstone.foodlog.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @Id @GeneratedValue
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_picture_id")
    private List<Picture> pictureList = new ArrayList<>();

    private Float rating;

    private String review;

    @Enumerated(EnumType.STRING)
    private FoodType type;

    @Enumerated(EnumType.STRING)
    private FoodPurpose purpose;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private Date date;

    @OneToMany(mappedBy = "comment")
    private List<Comment> commentList = new ArrayList<>();

}
