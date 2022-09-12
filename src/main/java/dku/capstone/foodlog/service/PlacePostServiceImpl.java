package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.PlacePost;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.PlacePostDto;
import dku.capstone.foodlog.repository.PlacePostRepository;
import dku.capstone.foodlog.repository.PlaceRepository;
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
    private final PlaceRepository placeRepository;

    @Transactional
    public void setAverageRating(PlacePost placePost) {
        placePost.setAverageRating(postRepository.getAverageRating(placePost.getPlace().getId()));
    }

    @Transactional
    public PlacePost savePlacePost(Place place, Integer rating) {
        PlacePost placePost = PlacePost.builder()
                .place(place)
                .postCount(1L)
                .averageRating(Float.valueOf(rating))
                .build();

        return placePostRepository.save(placePost);
    }

    public PlacePostDto.Detail getPlacePostDetail(Long placePostId) {
        PlacePost placePost = placePostRepository.findById(placePostId)
                .orElseThrow(() -> new NoSuchElementException("게시물이 없습니다."));

        return new PlacePostDto.Detail(placePost);
    }

}
