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

    @Id @GeneratedValue
    @Column(name = "place_id")
    private Long id;

    private Double latitude;

    private Double longitude;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Post> postList = new ArrayList<>();
}
