package dku.capstone.foodlog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
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

import static dku.capstone.foodlog.domain.QMenu.menu;
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

    private BooleanExpression ratingGoe(Float rating) {
        return rating != null? placePost.averageRating.goe(rating) : null;
    }

    private BooleanExpression postCountGt() {
        return placePost.postCount.gt(0);
    }

    private BooleanExpression latitudeBetween(Double latitude, Double latitudeDelta) {

        if (latitude != null) {
            if (latitudeDelta == null) {
                latitudeDelta = 0.05;
            }
            Double start = latitude - (latitudeDelta/2);
            Double end = latitude + (latitudeDelta/2);

            return place.latitude.between(start, end);
        } else {
            return null;
        }
    }

    private BooleanExpression longitudeBetween(Double longitude, Double longitudeDelta) {

        if (longitude != null) {
            if (longitudeDelta == null) {
                longitudeDelta = 0.05;
            }
            Double start = longitude - (longitudeDelta/2);
            Double end = longitude + (longitudeDelta/2);

            return place.longitude.between(start, end);
        } else {
            return null;
        }
    }

    private JPAQuery<Place> filterPlace(MapDto.Filter mapFilter) {

        //TODO if문으로 delta값으로 나눠서 bulk query 사용하면 디비에서 바로 지워줌

        return queryFactory
                .selectFrom(place)
                .where(
                        postCountGt(),
                        purposeEq(mapFilter.getPurposeList()),
                        categoryEq(mapFilter.getCategoryList()),
                        ratingGoe(mapFilter.getRating()),
                        latitudeBetween(mapFilter.getLatitude(), mapFilter.getLatitudeDelta()),
                        longitudeBetween(mapFilter.getLongitude(), mapFilter.getLongitudeDelta()))
                .join(place.placePost, placePost)
            .orderBy(place.placePost.averageRating.desc());
    }

    public List<Place> filterPlaceAll(MapDto.Filter mapFilter) {
        return filterPlace(mapFilter).fetch();
    }

    public List<Place> filterPlaceLimit(MapDto.Filter mapFilter) {
        return filterPlace(mapFilter)
            .limit(10)
            .fetch();
    }

    private BooleanExpression placeNameContains(String name) {
        return name != null? place.name.contains(name) : null;
    }

    private List<Place> searchPlaceByName(MapDto.Search mapSearch, Pageable pageable) {

        return queryFactory
                .selectFrom(place)
                .where(
                        latitudeBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                        longitudeBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
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
                        latitudeBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                        longitudeBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
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
                        latitudeBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                        longitudeBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
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
                latitudeBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                longitudeBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
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

    private List<Post> recommendPost(RecommendDto.Request recommendRequest, Member member, Pageable pageable) {
        List<Long> newPosts = queryFactory
            .select(post.id.max())
            .from(post)
            .where(
                placePostPurposeEq(recommendRequest.getFoodPurpose()),
                memberAgeBetween(member.getBirthday()),
                memberGenderEq(member.getGender())
            )
            .groupBy(place)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return queryFactory
            .selectFrom(post)
            .where(post.id.in(
                newPosts
            ))
            .fetch();
    }

    private Long getCountRecommendPost(RecommendDto.Request recommendRequest, Member member) {
        List<Long> newPosts = queryFactory
            .select(post.id.max())
            .from(post)
            .where(
                placePostPurposeEq(recommendRequest.getFoodPurpose()),
                memberAgeBetween(member.getBirthday()),
                memberGenderEq(member.getGender())
            )
            .groupBy(place)
            .fetch();

        return queryFactory
            .select(post.count())
            .from(post)
            .where(post.id.in(
                newPosts
            ))
            .fetchOne();
    }

    public Page<Post> getPageRecommendPost(RecommendDto.Request recommendRequest, Member member, Pageable pageable) {
        List<Post> content = recommendPost(recommendRequest, member, pageable);
        Long count = getCountRecommendPost(recommendRequest, member);
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression menuFoodCategoryContains(String name) {
        return name != null? menu.food.contains(name) : null;
    }

    private List<Place> searchPlaceByMenu(MapDto.Search mapSearch, Pageable pageable) {
        return queryFactory
            .selectFrom(place)
            .where(
                latitudeBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                longitudeBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
                place.category.in(
                        JPAExpressions
                            .select(menu.foodCategory)
                            .from(menu)
                            .where(menuFoodCategoryContains(mapSearch.getQuery()))
                    )
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private Long getCountSearchPlaceByMenu(MapDto.Search mapSearch) {
        return queryFactory
            .select(place.count())
            .from(place)
            .where(
                latitudeBetween(mapSearch.getLatitude(), mapSearch.getLatitudeDelta()),
                longitudeBetween(mapSearch.getLongitude(), mapSearch.getLongitudeDelta()),
                place.category.in(
                    JPAExpressions
                        .select(menu.foodCategory)
                        .from(menu)
                        .where(menuFoodCategoryContains(mapSearch.getQuery()))
            ))
            .fetchOne();
    }

    public Page<Place> getPageSearchPlaceByMenu(MapDto.Search mapSearch, Pageable pageable) {
        List<Place> content = searchPlaceByMenu(mapSearch, pageable);
        Long count = getCountSearchPlaceByMenu(mapSearch);
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression postMemberEq(Long memberId) {
        return memberId != null? post.member.id.eq(memberId) : null;
    }

    public List<Place> getPlaceByMember(Long memberId) {
        return queryFactory
            .select(post.place).distinct()
            .from(post)
            .where(
                postMemberEq(memberId)
            )
            .fetch();
    }
}
