package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    List<Member> findAllByUsername(String username);

    Page<Member> findAllByUsernameContains(String username, Pageable pageable);
}
