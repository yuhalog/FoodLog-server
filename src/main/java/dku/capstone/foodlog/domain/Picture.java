package dku.capstone.foodlog.domain;

import javax.persistence.*;

@Entity
public class Picture extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "picture_id")
    private Long id;

    private String pictureUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
