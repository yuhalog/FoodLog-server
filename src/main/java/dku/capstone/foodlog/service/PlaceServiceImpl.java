package dku.capstone.foodlog.service;

import dku.capstone.foodlog.constant.FoodCategory;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.request.KakaoPlaceRequest;
import dku.capstone.foodlog.dto.response.PlacePostDto;
import dku.capstone.foodlog.dto.response.KakaoPlaceResponse;
import dku.capstone.foodlog.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepository placeRepository;

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
//                .queryParam("page", "1")
//                .queryParam("size", "15")
                .queryParam("query", "맛집 식당")
                .build();

        ResponseEntity<KakaoPlaceResponse> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, KakaoPlaceResponse.class);
        System.out.println(uriComponents.toUriString());
        System.out.println(response.getBody().getDocuments().toString());

        return response.getBody();
    }

    public Place checkPlaceInDb(KakaoPlaceRequest kakaoPlaceRequest) {
        Place place = placeRepository.findByKakaoPlaceId(Long.parseLong(kakaoPlaceRequest.getId()));

        if (place!=null) {
            return place;
        } else {
            KakaoPlaceResponse kakaoPlaceResponse = kakaoPlaceQueryApi(kakaoPlaceRequest);
            FoodCategory foodCategory = parsingCategory(kakaoPlaceResponse.getDocuments().get(0).getCategory());
            Place newPlace = savePlace(kakaoPlaceResponse.getDocuments().get(0), foodCategory);
            return newPlace;
        }
    }

    private FoodCategory parsingCategory(String kakaoCategory) {
        String category = kakaoCategory.split(" > ")[1];
        try {
            return FoodCategory.valueOfFoodCategory(category);
        } catch (Exception e) {
            return FoodCategory.ETC;
        }
    }

    private Place savePlace(KakaoPlaceResponse.PlaceInfo placeInfo, FoodCategory foodCategory) {
        Place place = Place.builder()
                .name(placeInfo.getName())
                .address(placeInfo.getAddress())
                .latitude(Double.parseDouble(placeInfo.getLatitude()))
                .longitude(Double.parseDouble(placeInfo.getLongitude()))
                .kakaoPlaceId(Long.parseLong(placeInfo.getId()))
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