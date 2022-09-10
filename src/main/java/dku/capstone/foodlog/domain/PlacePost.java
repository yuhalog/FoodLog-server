package dku.capstone.foodlog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PlacePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_post_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private Long postCount;

    private Float averageRating;

    @Builder
    public PlacePost(Place place, Long postCount, Float averageRating) {
        this.place = place;
        this.postCount = postCount;
        this.averageRating = averageRating;
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

    //별점이 바뀔 때마다 실행되는 메서드 (별점 계산)
//    public float calAverageRating(int rating){
//        sumRating += rating;
//        averageRating = Math.round((sumRating / (float)postCount) * 10) / (float)10.0;
//        return averageRating;
//    }

}
