package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe findByMemberAndSubscriber(Member member, Member Subscribe);

    Page<Subscribe> findByMember(Member member, Pageable pageable);

    Page<Subscribe> findBySubscriber(Member member, Pageable pageable);

}
