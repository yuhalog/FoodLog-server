package dku.capstone.foodlog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Picture extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "picture_id")
    private Long id;

    private String postUrl;

}
