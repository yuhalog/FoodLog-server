package dku.capstone.foodlog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dku.capstone.foodlog.constant.FoodCategory;
import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.MapDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dku.capstone.foodlog.domain.QPlace.place;
import static dku.capstone.foodlog.domain.QPlacePost.placePost;

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

    public List<Place> searchPlace(MapDto.Request mapRequest) {

        return queryFactory
                .selectFrom(place)
                .where(
                        postCountGt(),
                        purposeEq(mapRequest.getPurposeList()),
                        categoryEq(mapRequest.getCategoryList()),
                        ratingGoe(mapRequest.getRating()),
                        coordinateBetween(mapRequest.getLatitude(), mapRequest.getLatitudeDelta()),
                        coordinateBetween(mapRequest.getLongitude(), mapRequest.getLongitudeDelta()))
                .join(place.placePost, placePost)
                .fetch();
    }
}
