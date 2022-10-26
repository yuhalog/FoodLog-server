package dku.capstone.foodlog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dku.capstone.foodlog.domain.QPost.post;
import static dku.capstone.foodlog.domain.QSubscribe.subscribe;

@RequiredArgsConstructor
@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private BooleanExpression subscribeMemberEq(Member member) {
        return member!=null? subscribe.member.eq(member) : null;
    }

    private List<Post> subscribePosts(Member member) {
        return queryFactory
            .selectFrom(post)
            .where(post.member.in(
                JPAExpressions
                    .select(subscribe.subscriber)
                    .from(subscribe)
                    .where(
                        subscribeMemberEq(member)
                    )
            ))
            .orderBy(post.id.desc())
            .fetch();
    }

    private Long subscribePostsCount(Member member) {
        return queryFactory
            .select(post.count())
            .from(post)
            .where(post.member.in(
                JPAExpressions
                    .select(subscribe.subscriber)
                    .from(subscribe)
                    .where(
                        subscribeMemberEq(member)
                    )
            ))
            .fetchOne();
    }

    public Page<Post> getPageSubscribePosts(Member member, Pageable pageable) {
        List<Post> content = subscribePosts(member);
        Long count = subscribePostsCount(member);
        return new PageImpl<>(content, pageable, count);
    }
}
