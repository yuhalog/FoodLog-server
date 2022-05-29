package dku.capstone.foodlog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    private Double latitude;

    private Double longitude;

    @OneToOne(mappedBy = "place")
    private PlacePost placePost;

    @Builder
    public Place(Double latitude,
                 Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
