package dku.capstone.foodlog.domain;

import lombok.AccessLevel;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private Integer postCount;

    private Float averageRating;

    //post의 수를 하나씩 늘리는 메서드
    public int addPostCount(){
        this.postCount += 1;
        return postCount;
    }

    //post의 수를 하나씩 줄이는 메서드
    public int removePostCount(){
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
