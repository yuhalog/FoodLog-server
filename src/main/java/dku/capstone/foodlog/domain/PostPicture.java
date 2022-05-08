package dku.capstone.foodlog.domain;

import javax.persistence.*;

@Entity
public class PostPicture extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "post_picture_id")
    private Long id;

    private String pictureUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
