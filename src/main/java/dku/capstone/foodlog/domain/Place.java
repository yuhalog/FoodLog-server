package dku.capstone.foodlog.domain;

import lombok.AccessLevel;
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

    private String Address;

    private int post_count;

    private Float average_rating;
}
