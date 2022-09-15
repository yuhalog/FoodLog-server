package dku.capstone.foodlog.domain;

import dku.capstone.foodlog.constant.FoodCategory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Place extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    private Long kakaoPlaceId;

    @Builder.Default
    @OneToMany(mappedBy = "place")
    private List<Post> postList = new ArrayList<Post>();

    @OneToOne(mappedBy = "place")
    private PlacePost placePost;

    private Double latitude;

    private Double longitude;

    private String name;

    private String address;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    @Builder
    public Place(Long kakaoPlaceId, Double latitude, Double longitude, String name, String address, FoodCategory category) {
        this.kakaoPlaceId = kakaoPlaceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
        this.category = category;
    }

    public void setPlacePost(PlacePost placePost) {
        this.placePost = placePost;
    }

}
