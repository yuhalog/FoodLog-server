package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.*;
import dku.capstone.foodlog.dto.PostDto;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PlaceService placeService;
    private final PlacePostService placePostService;
    private final PostPictureService postPictureService;

    @Transactional
    public PostDto.Response createPost(Member member, PostDto.Request postRequest, List<String> imageNameList) {

        Place place = placeService.checkPlaceInDb(postRequest);
        Post savedPost = savePost(member, place, postRequest);
        postPictureService.savePostPictureList(imageNameList, savedPost);
        placePostService.addPlacePost(savedPost.getPlace().getPlacePost());

        // TODO 메소드 분리하기
        return new PostDto.Response(savedPost);
    }

    private Post savePost(Member member, Place place, PostDto.Request postRequest) {

        Post newPost = Post.builder()
                .member(member)
                .rating(postRequest.getRating())
                .review(postRequest.getReview())
                .date(LocalDate.parse(postRequest.getDate(), DateTimeFormatter.ISO_DATE))
                .place(place)
                .purpose(postRequest.getPurpose())
                .build();

        return postRepository.save(newPost);
    }

    @Transactional
    public PostDto.Response updatePost(Long postId, PostDto.Request postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시물이 없습니다."));
        post.setReview(postRequest.getReview());

        return new PostDto.Response(post);
    }

    public PostDto.Response getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시물이 없습니다."));

        return new PostDto.Response(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시물이 없습니다."));

        post.getPlace().getPostList().remove(post);
        postPictureService.deletePostPictureList(post.getPictureList());
        placePostService.removePlacePost(post.getPlace().getPlacePost());
        postRepository.delete(post);
    }
}

//    public Place findPlaceBySavePost(PostFormDto postFormDto) {
//        PlacePost placePost = null;
//        //해당 위치의 place가 DB에 있는지 확인
//        try {
//            placePost = placeRepository.findByLatitudeAndLongitude(postFormDto.getLatitude(), postFormDto.getLongitude());
//
//            placePost.plusCountPost();
//            placePost.calAverageRating(postFormDto.getRating());
//
//        } catch (Exception e) {
//            placePost = Place.builder()
//                    .name(postFormDto.getPlaceName())
//                    .address(postFormDto.getPlaceAddress())
//                    .latitude(postFormDto.getLatitude())
//                    .longitude(postFormDto.getLongitude())
//                    .postCount(1)
//                    .averageRating((float) postFormDto.getRating())
//                    .sumRating((float) postFormDto.getRating())
//                    .build();
//
//            placeRepository.save(place);
//        }
//        return place;
//    }
//
//    //delete post
//    public void deletePost(Long postId){
//        //Place 계산
//        Post post = postRepository.findById(postId).get();
//
//        //post의 place를 찾음
//        Double latitude = post.getPlace().getLatitude();
//        Double longitude = post.getPlace().getLongitude();
//        Place place = placeRepository.findByLatitudeAndLongitude(latitude, longitude);
//
//        //place의 post List에서 해당 post를 삭제 & 별점 계산
//        place.getPostList().remove(post);
//        place.minusCountPost();
//        place.calAverageRating(post.getRating());
//
//        postRepository.deleteById(postId);
//    }
//
//
//
//    private List<Post> getPosts(Long id, Pageable page) {
//        return id == null ?
//                this.postRepository.findAllByOrderByIdDesc(page) :
//                this.postRepository.findByIdLessThanOrderByIdDesc(id, page);
//    }
//
//    private Boolean hasNext(Long id) {
//        if (id == null) return false;
//        return this.postRepository.existsByIdLessThan(id);
//    }
//}
