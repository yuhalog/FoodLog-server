package dku.capstone.foodlog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dku.capstone.foodlog.constant.FoodCategory;
import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.constant.Gender;
import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.MapDto;
import dku.capstone.foodlog.dto.RecommendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static dku.capstone.foodlog.domain.QPlace.place;
import static dku.capstone.foodlog.domain.QPlacePost.placePost;
import static dku.capstone.foodlog.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private BooleanExpression purposeEq(List<FoodPurpose> purposeList) {
        return !purposeList.isEmpty()? placePost.purpose.in(purposeList) : null;
    }

    private BooleanExpression categoryEq(List<FoodCategory> categoryList) {
        return !categoryList.isEmpty()? place.category.in(categoryList) : null;
    }

    private BooleanExpression ratingGoe(Integer rating) {
        return rating != null? placePost.averageRating.goe(rating) : null;
    }

    private BooleanExpression postCountGt() {
        return placePost.postCount.gt(0);
    }

    private BooleanExpression coordinateBetween(Double coordinate, Double coordinateDelta) {

        if (coordinate!=null || coordinateDelta!=null) {
            Double start = coordinate - (coordinateDelta/2);
            Double end = coordinate + (coordinateDelta/2);

            return placePost.averageRating.between(start, end);
        } else {
            return null;
        }
    }

    public List<Place> searchPlace(MapDto.Filter mapFilter) {

        return queryFactory
                .selectFrom(place)
                .where(
                        postCountGt(),
                        purposeEq(mapFilter.getPurposeList()),
                        categoryEq(mapFilter.getCategoryList()),
                        ratingGoe(mapFilter.getRating()),
                        coordinateBetween(mapFilter.getLatitude(), mapFilter.getLatitudeDelta()),
                        coordinateBetween(mapFilter.getLongitude(), mapFilter.getLongitudeDelta()))
                .join(place.placePost, placePost)
                .fetch();
    }

    private BooleanExpression placeNameContains(String name) {
        return name != null? place.name.contains(name) : null;
    }

    private List<Place> searchPlaceByName(MapDto.Search mapSearch, Pageable pageable) {

        return queryFactory
                .selectFrom(place)
                .where(
                        coordinateBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                        coordinateBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
                        placeNameContains(mapSearch.getQuery())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Long getCountSearchPlaceByName(MapDto.Search mapSearch) {

        return queryFactory
                .select(place.count())
                .from(place)
                .where(
                        coordinateBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                        coordinateBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
                        placeNameContains(mapSearch.getQuery())
                )
                .fetchOne();
    }

    public Page<Place> getPageSearchPlaceByName(MapDto.Search mapSearch, Pageable pageable) {
        List<Place> content = searchPlaceByName(mapSearch, pageable);
        Long count = getCountSearchPlaceByName(mapSearch);
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression placeAddressContains(String address) {
        return address != null? place.address.contains(address) : null;
    }

    private List<Place> searchPlaceByAddress(MapDto.Search mapSearch, Pageable pageable) {
        return queryFactory
                .selectFrom(place)
                .where(
                        coordinateBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                        coordinateBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
                        placeAddressContains(mapSearch.getQuery())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Long getCountSearchPlaceByAddress(MapDto.Search mapSearch) {

        return queryFactory
                .select(place.count())
                .from(place)
                .where(
                        coordinateBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                        coordinateBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
                        placeAddressContains(mapSearch.getQuery())
                )
                .fetchOne();
    }

    public Page<Place> getPageSearchPlaceByAddress(MapDto.Search mapSearch, Pageable pageable) {
        List<Place> content = searchPlaceByAddress(mapSearch, pageable);
        Long count = getCountSearchPlaceByAddress(mapSearch);
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression placePostPurposeEq(FoodPurpose foodPurpose) {
        return foodPurpose != null? post.place.placePost.purpose.eq(foodPurpose) : null;
    }

    private BooleanExpression memberAgeBetween(LocalDate birthday) {
        return birthday != null? post.member.birthday.between(birthday.minusYears(5), birthday.plusYears(5)) : null;
    }

    private BooleanExpression memberGenderEq(Gender gender) {
        return gender != null? post.member.gender.eq(gender) : null;
    }

    private List<Post> recommendPost(RecommendDto.Request recommendRequest, Member member) {
        return queryFactory
                .selectFrom(post)
                .where(
                        placePostPurposeEq(recommendRequest.getFoodPurpose()),
                        memberAgeBetween(member.getBirthday()),
                        memberGenderEq(member.getGender())
                )
//                .orderBy(NumberExpression.random().asc())
                .fetch();
    }

    private Long getCountRecommendPost(RecommendDto.Request recommendRequest, Member member) {
        return queryFactory
                .select(post.count())
                .from(post)
                .where(
                        placePostPurposeEq(recommendRequest.getFoodPurpose()),
                        memberAgeBetween(member.getBirthday()),
                        memberGenderEq(member.getGender())
                )
                .fetchOne();
    }

    public Page<Post> getPageRecommendPost(RecommendDto.Request recommendRequest, Member member, Pageable pageable) {
        List<Post> content = recommendPost(recommendRequest, member);
        Long count = getCountRecommendPost(recommendRequest, member);
        return new PageImpl<>(content, pageable, count);
    }

}
