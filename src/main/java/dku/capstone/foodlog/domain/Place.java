package dku.capstone.foodlog.domain;

import dku.capstone.foodlog.constant.FoodCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @OneToMany(mappedBy = "place")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "placePost")
    private List<Post> placePostList = new ArrayList<>();

    private Double latitude;

    private Double longitude;

    private String name;

    private String address;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    @Builder
    public Place(Double latitude, Double longitude, String name, String address, FoodCategory category) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
        this.category = category;
    }
}
