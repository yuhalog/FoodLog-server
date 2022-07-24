package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;
import dku.capstone.foodlog.dto.request.PostFormDto;
import dku.capstone.foodlog.dto.request.PostReviewOnly;
import dku.capstone.foodlog.dto.response.CursorResult;
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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final PostPictureRepository postPictureRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    public Place findPlaceBySavePost(PostFormDto postFormDto) {
        Place place = null;
        //해당 위치의 place가 DB에 있는지 확인
        try {
            place = placeRepository.findByLatitudeAndLongitude(postFormDto.getLatitude(), postFormDto.getLongitude());

            place.plusCountPost();
            place.calAverageRating(postFormDto.getRating());

        } catch (Exception e) {
            place = Place.builder()
                    .name(postFormDto.getPlaceName())
                    .address(postFormDto.getPlaceAddress())
                    .latitude(postFormDto.getLatitude())
                    .longitude(postFormDto.getLongitude())
                    .postCount(1)
                    .averageRating((float) postFormDto.getRating())
                    .sumRating((float) postFormDto.getRating())
                    .build();

            placeRepository.save(place);
        }
        return place;
    }

    public List<PostPicture> savePostPicture(List<String> pictureImgList, Post post){
        List<PostPicture> postPictureList = new ArrayList<>();

        for (String pictureImg : pictureImgList) {
            PostPicture postPicture = PostPicture.builder()
                    .pictureUrl(pictureImg)
                    .post(post)
                    .build();

            postPictureRepository.save(postPicture);

            postPictureList.add(postPicture);
        }

        return postPictureList;
    }
    //create post
    public PostFormDto createPost(PostFormDto postFormDto, List<String> pictureImgList){

        Place place = findPlaceBySavePost(postFormDto);

        Member member = memberRepository.findById(postFormDto.getMemberId())
                .orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없음"));

        //picture을 리스트 형태로 처리 & 저장


        Post post = Post.builder()
                .member(member)
                .rating(postFormDto.getRating())
                .review(postFormDto.getReview())
                .type(postFormDto.getType())
                .purpose(postFormDto.getPurpose())
                .place(place)
                .build();

        List<PostPicture> postPictureList = savePostPicture(pictureImgList, post);

        //place의 post List에 post를 추가
//        place.getPostList().add(post);
        postRepository.save(post);

        PostFormDto newPost = new PostFormDto(post, pictureImgList);

        return newPost;

    }

    //see post
    public Post seePost(Long postId){
        Post post = postRepository.getById(postId);
        return post;
    }

    //edit post
    public String editPost(PostReviewOnly postReviewOnly, Long postId){
        Post findPost = postRepository.getById(postId);

        findPost.setReview(postReviewOnly.getReview());

        postRepository.save(findPost);

        return "";
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
