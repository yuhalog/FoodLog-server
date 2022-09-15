package dku.capstone.foodlog.domain;

import dku.capstone.foodlog.constant.FoodPurpose;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PlacePost extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_post_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private Long postCount;

    private Float averageRating;

    @Enumerated(EnumType.STRING)
    private FoodPurpose purpose;

    @Builder
    public PlacePost(Place place, Long postCount, Float averageRating, FoodPurpose purpose) {
        this.place = place;
        this.postCount = postCount;
        this.averageRating = averageRating;
        this.purpose = purpose;
    }

    public void setPurpose(FoodPurpose purpose) {
        this.purpose = purpose;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Long addPostCount(){
        this.postCount += 1;
        return postCount;
    }

    public Long removePostCount(){
        this.postCount -= 1;
        return postCount;
    }

}
