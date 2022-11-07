package dku.capstone.foodlog.service;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.PlacePost;
import dku.capstone.foodlog.dto.PlacePostDto;
import dku.capstone.foodlog.dto.PostDto;
import dku.capstone.foodlog.repository.PlacePostRepository;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlacePostServiceImpl implements PlacePostService{

    private final PlacePostRepository placePostRepository;
    private final PostRepository postRepository;

    @Transactional
    public void removePlacePost(PlacePost placePost) {
        placePost.removePostCount();
        setPlacePost(placePost);
    }

    @Transactional
    public void addPlacePost(PlacePost placePost) {
        placePost.addPostCount();
        setPlacePost(placePost);
    }

    public void setPlacePost(PlacePost placePost) {
        setAverageRating(placePost);
        setPurpose(placePost);
    }

    private void setAverageRating(PlacePost placePost) {
        placePost.setAverageRating(postRepository.getAverageRating(placePost.getPlace().getId()));
    }

    private void setPurpose(PlacePost placePost) {
        placePost.setPurpose(FoodPurpose.valueOf(postRepository.findWithPagingByPurpose(placePost.getPlace().getId()).get(0)));
    }

    @Transactional
    public PlacePost savePlacePost(Place place, PostDto.Request postRequest) {
        PlacePost placePost = PlacePost.builder()
                .place(place)
                .postCount(0L)
                .averageRating(Float.valueOf(postRequest.getRating()))
                .purpose(postRequest.getPurpose())
                .build();

        return placePostRepository.save(placePost);
    }

    public PlacePostDto.Detail getPlacePostDetail(Long placePostId) {
        PlacePost placePost = placePostRepository.findById(placePostId)
                .orElseThrow(() -> new NoSuchElementException("게시물이 없습니다."));

        return new PlacePostDto.Detail(placePost);
    }

    @Transactional
    public void deletePlacePost(PlacePost placePost) {
        placePostRepository.delete(placePost);
    }
}
