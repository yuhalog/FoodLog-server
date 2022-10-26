package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostRepositoryCustom {

    Page<Post> getPageSubscribePosts(Member member, Pageable pageable);

}
