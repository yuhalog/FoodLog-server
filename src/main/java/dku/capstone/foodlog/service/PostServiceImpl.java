package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.*;
import dku.capstone.foodlog.dto.request.PostRequest;
import dku.capstone.foodlog.dto.request.PostReviewOnly;
import dku.capstone.foodlog.dto.response.CursorResult;
import dku.capstone.foodlog.dto.response.PostResponse;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.repository.PlaceRepository;
import dku.capstone.foodlog.repository.PostPictureRepository;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PlaceService placeService;
    private final PostPictureService postPictureService;

    public PostResponse createPost(Member member, PostRequest postRequest, List<String> pictureUrlList) {

        Place place = placeService.checkPlaceInDb(postRequest.getKakaoPlace());
        Post savedPost = savePost(member, place, postRequest);
        postPictureService.savePostPictureList(pictureUrlList, savedPost);

        return new PostResponse(savedPost, pictureUrlList);
    }

    private Post savePost(Member member, Place place, PostRequest postRequest) {

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
//
//    //create post
//    public PostFormDto createPost(PostFormDto postFormDto, List<String> pictureImgList){
//
//        Place place = findPlaceBySavePost(postFormDto);
//
//        Member member = memberRepository.findById(postFormDto.getMemberId())
//                .orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없음"));
//
//        Post post = Post.builder()
//                .member(member)
//                .rating(postFormDto.getRating())
//                .review(postFormDto.getReview())
//                .purpose(postFormDto.getPurpose())
//                .place(place)
//                .date(LocalDate.parse(postFormDto.getDate(), DateTimeFormatter.ISO_DATE))
//                .build();
//
//        postRepository.save(post);
//
//        savePostPicture(pictureImgList, post);
//
//        PostFormDto newPost = new PostFormDto(post, pictureImgList);
//
//        return newPost;
//    }
//
//    //see post
//    @Transactional(readOnly = true)
//    public PostFormDto getPost(Long postId){
//        Post post = postRepository.getById(postId);
//        PostFormDto postFormDto = new PostFormDto(post);
//        return postFormDto;
//    }
//
//    //edit post
//    public String updatePostReview(PostReviewOnly postReviewOnly, Long postId){
//        Post post = postRepository.getById(postId);
//        post.setReview(postReviewOnly.getReview());
//        String review = postReviewOnly.getReview();
//        return review;
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
//    public CursorResult<Post> get(Long cursorId, Pageable page) {
//        final List<Post> posts = getPosts(cursorId, page);
//        final Long lastIdOfList = posts.isEmpty() ?
//                null : posts.get(posts.size() - 1).getId();
//
//        return new CursorResult<>(posts, hasNext(lastIdOfList));
//    }
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
