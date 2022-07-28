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

    @Column(name="address")
    private String address;

    //postList.size()
    private Integer post_count;

    private Float average_rating;

    private Float sum_rating;

    @Builder
    public Place(Double latitude,
                 Double longitude,
                 String name,
                 String address,
                 Integer post_count,
                 Float average_rating,
                 Float sum_rating){
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
        this.post_count = post_count;
        this.average_rating = average_rating;
        this.sum_rating = sum_rating;
    }

    //post의 수를 하나씩 늘리는 메서드
    public int plusCountPost(){
        this.post_count += 1;
        return post_count;
    }

    //post의 수를 하나씩 줄이는 메서드
    public int minusCountPost(){
        this.post_count -= 1;
        return post_count;
    }

    //별점이 바뀔 때마다 실행되는 메서드 (별점 계산)
    public float calAverageRating(int rating){
        sum_rating += rating;
        average_rating = Math.round((sum_rating / (float)post_count) * 10) / (float)10.0;
        return average_rating;
    }
}
