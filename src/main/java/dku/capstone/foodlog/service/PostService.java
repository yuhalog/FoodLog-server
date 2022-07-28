package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;
import dku.capstone.foodlog.dto.request.PostFormDto;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final PostPictureRepository postPictureRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    //create post
    public Post createPost(PostFormDto postFormDto, List<String> pictureImgList){

        //client에서 위도, 경도 정보 가져옴
        //해당 위치의 place가 DB에 있는지 확인

        /*Member member = memberRepository.findById(postFormDto.getMemberId())
                .orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없음"));*/

        Place place = placeRepository.findByLatitudeAndLongitude(postFormDto.getLocation().get(0), postFormDto.getLocation().get(1));

        if (place == null){
            place = Place.builder()
                    .latitude(postFormDto.getLocation().get(0))
                    .longitude(postFormDto.getLocation().get(1))
                    .name(postFormDto.getName())
                    .address(postFormDto.getAddress())
                    .post_count(0)
                    .average_rating(0.0F)
                    .sum_rating(0.0F)
                    .build();
            placeRepository.save(place);
        }

        //postPlace 별점 계산 로직
        place.plusCountPost();
        place.calAverageRating(postFormDto.getRating());

        //picture을 리스트 형태로 처리 & 저장
        List<PostPicture> postPictureList = new ArrayList<>();

        for (String pictureImg : pictureImgList) {
            PostPicture postPicture = PostPicture.builder()
                    .pictureUrl(pictureImg)
                    .build();
            postPictureList.add(postPicture);
            postPictureRepository.save(postPicture);
        }

        Post post = Post.builder()
                .member(null)
                .pictureList(postPictureList)
                .rating(postFormDto.getRating())
                .review(postFormDto.getReview())
                .type(postFormDto.getType())
                .purpose(postFormDto.getPurpose())
                .place(place)
                .date(null)
                .build()
                ;

        //place의 post List에 post를 추가
        place.getPostList().add(post);
        placeRepository.save(place);

        return postRepository.save(post);
    }

    //see post
    public PostResponse seePost(Long postId){
        Post post = postRepository.findById(postId).get();

        List<String> pictureList = new ArrayList<>();

        for (PostPicture postPicture: post.getPictureList()) {
            pictureList.add(postPicture.getPictureUrl());
        }
        PostResponse postResponse = new PostResponse(post.getMember(),
                pictureList,
                post.getRating(),
                post.getReview(),
                post.getType(),
                post.getPurpose(),
                post.getPlace().getName(),
                post.getPlace().getAddress(),
                "20220729");
        return postResponse;
    }

    //edit post
    public PostResponse editPost(PostReviewOnly postReviewOnly, Long postId){
        Post findPost = postRepository.findById(postId).get();
        findPost.setReview(postReviewOnly.getReview());
        postRepository.save(findPost);

        return seePost(postId);
    }

    //delete post
    public void deletePost(Long postId){
        //Place 계산
        Post post = postRepository.findById(postId).get();

        //post의 place를 찾음
        Double latitude = post.getPlace().getLatitude();
        Double longitude = post.getPlace().getLongitude();
        Place place = placeRepository.findByLatitudeAndLongitude(latitude, longitude);

        //place의 post List에서 해당 post를 삭제 & 별점 계산
        place.getPostList().remove(post);
        place.minusCountPost();
        place.calAverageRating(post.getRating());

        postRepository.deleteById(postId);
    }


    public CursorResult<Post> get(Long cursorId, Pageable page) {
        final List<Post> posts = getPosts(cursorId, page);
        final Long lastIdOfList = posts.isEmpty() ?
                null : posts.get(posts.size() - 1).getId();

        return new CursorResult<>(posts, hasNext(lastIdOfList));
    }

    private List<Post> getPosts(Long id, Pageable page) {
        return id == null ?
                this.postRepository.findAllByOrderByIdDesc(page) :
                this.postRepository.findByIdLessThanOrderByIdDesc(id, page);
    }

    private Boolean hasNext(Long id) {
        if (id == null) return false;
        return this.postRepository.existsByIdLessThan(id);
    }
}
