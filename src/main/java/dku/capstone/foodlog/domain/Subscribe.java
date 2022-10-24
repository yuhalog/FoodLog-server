package dku.capstone.foodlog.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subscribe extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscribe_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "subscriber")
    private Member subscriber;

    @Builder
    public Subscribe(Member member, Member subscriber) {
        this.member = member;
        this.subscriber = subscriber;
    }
}
