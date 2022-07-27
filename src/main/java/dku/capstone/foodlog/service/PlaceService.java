package dku.capstone.foodlog.service;

import com.querydsl.core.BooleanBuilder;
import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.constant.FoodType;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.QPlace;
import dku.capstone.foodlog.dto.request.MapRequest;
import dku.capstone.foodlog.dto.response.MapDto;
import dku.capstone.foodlog.dto.response.PlacePostDto;
import dku.capstone.foodlog.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;


//    /**
//     * 장소 정보 조회 (구글 API)
//     */
//    public Response getPlaceDetail() {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = okhttp3.MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name%2Crating%2Cformatted_phone_number&key=" + API_KEY)
//                .method("GET", body)
//                .build();
//        Response response = client.newCall(request).execute();
//    }

//    /**
//     * 지도 조회 (+필터조건 포함하기)
//     */
//    public MapDto getMapPlace(MapRequest request) {
//
//        FoodPurpose purpose = request.getPurpose();
//        FoodType type = request.getType();
//        Float rating = request.getRating();
//
//        QPlace place = QPlace.place;
//
//        BooleanBuilder builder = new BooleanBuilder();
//
//        if (purpose != null){
//            builder.and(place.purpose.eq(purpose));
//        }
//        if (type != null) {
//            builder.and(place.type.eq(type));
//        }
//        if (rating != null) {
//            builder.and(place.averageRating.goe(rating));
//        }
//        List<MapDto> list = queryFactory.selectFrom(place)
//                .where(boolean)
//        .fetch();
//
//    }

    /**
     * 중간 게시물 조회
     */
    public PlacePostDto getPlacePost(Long placeId) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NoSuchElementException("장소가 없습니다."));

        List<PlacePostDto.PictureReview> pictureReviews = new ArrayList<>();

        for (Post post : place.getPostList()) {
            PlacePostDto.PictureReview pictureReview = new PlacePostDto.PictureReview(post);
            pictureReviews.add(pictureReview);
        }

        PlacePostDto placePostDto = new PlacePostDto(place, pictureReviews);

        return placePostDto;
    }
}