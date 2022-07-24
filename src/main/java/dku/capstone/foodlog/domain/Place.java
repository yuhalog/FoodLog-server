package dku.capstone.foodlog.domain;

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

    private Double latitude;

    private Double longitude;

    private String name;

    private String address;

    //postList.size()
    private Integer postCount;

    private Float averageRating;

    private Float sumRating;

    @Builder
    public Place(String name, String address, Double latitude, Double longitude,
                 Integer postCount, Float averageRating, Float sumRating){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.postCount = postCount;
        this.averageRating = averageRating;
        this.sumRating = sumRating;
    }

    //post의 수를 하나씩 늘리는 메서드
    public int plusCountPost(){
        this.postCount += 1;
        return postCount;
    }

    //post의 수를 하나씩 줄이는 메서드
    public int minusCountPost(){
        this.postCount -= 1;
        return postCount;
    }

    //별점이 바뀔 때마다 실행되는 메서드 (별점 계산)
    public float calAverageRating(int rating){
        sumRating += rating;
        averageRating = Math.round((sumRating / (float)postCount) * 10) / (float)10.0;
        return averageRating;
    }
}
