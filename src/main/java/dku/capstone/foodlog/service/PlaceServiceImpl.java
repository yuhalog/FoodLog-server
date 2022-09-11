package dku.capstone.foodlog.service;

import dku.capstone.foodlog.constant.FoodCategory;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.PlacePost;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.request.KakaoPlaceRequest;
import dku.capstone.foodlog.dto.request.PlaceRequest;
import dku.capstone.foodlog.dto.request.PostRequest;
import dku.capstone.foodlog.dto.response.PlacePostDto;
import dku.capstone.foodlog.dto.response.KakaoPlaceResponse;
import dku.capstone.foodlog.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepository placeRepository;
    private final PlacePostService placePostService;

    private KakaoPlaceResponse kakaoPlaceQueryApi(KakaoPlaceRequest kakaoPlaceRequest) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        final String uri = "https://dapi.kakao.com/v2/local/search/keyword.json";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK 59bbe1a2aace1137ef1cc6629b4d07f4");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParam("page", kakaoPlaceRequest.getPage())
                .queryParam("size", kakaoPlaceRequest.getSize())
                .queryParam("query", kakaoPlaceRequest.getQuery())
                .queryParam("x", kakaoPlaceRequest.getLongitude())
                .queryParam("y", kakaoPlaceRequest.getLatitude())
                .build();

        ResponseEntity<KakaoPlaceResponse> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, KakaoPlaceResponse.class);
        System.out.println(uriComponents.toUriString());
        System.out.println(response.getBody().getDocuments().toString());

        return response.getBody();
    }

    @Transactional
    public Place checkPlaceInDb(PostRequest postRequest) {
        Place place = placeRepository.findByKakaoPlaceId(Long.parseLong(postRequest.getPlace().getKakaoId()));

        if (place!=null) {
            place.getPlacePost().addPostCount();
            return place;
        } else {
            return createPlace(postRequest.getPlace(), postRequest.getRating());
        }
    }

    private Place createPlace(PlaceRequest placeRequest, Integer rating) {
        FoodCategory foodCategory = parsingCategory(placeRequest.getCategory());
        Place place = savePlace(placeRequest, foodCategory);
        PlacePost placePost = placePostService.savePlacePost(place, rating);
        place.setPlacePost(placePost);
        return place;
    }

    private FoodCategory parsingCategory(String category) {
        FoodCategory foodCategory = FoodCategory.valueOfFoodCategory(category);

        if (foodCategory!=null) {
            return foodCategory;
        }
        return FoodCategory.ETC;
    }

    @Transactional
    Place savePlace(PlaceRequest placeRequest, FoodCategory foodCategory) {
        Place place = Place.builder()
                .name(placeRequest.getName())
                .address(placeRequest.getAddress())
                .latitude(Double.parseDouble(placeRequest.getLatitude()))
                .longitude(Double.parseDouble(placeRequest.getLongitude()))
                .kakaoPlaceId(Long.parseLong(placeRequest.getKakaoId()))
                .category(foodCategory)
                .build();

        return placeRepository.save(place);
    }

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