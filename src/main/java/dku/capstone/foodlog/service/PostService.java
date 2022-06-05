package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;
import dku.capstone.foodlog.dto.request.PostFormDto;
import dku.capstone.foodlog.dto.request.PostReviewOnly;
import dku.capstone.foodlog.repository.PostPictureRepository;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    //create post
    public Post createPost(PostFormDto postFormDto, List<String> pictureImgList){

        //place 생성 -> post 생성
        //client에서 위도, 경도 정보 가져옴
        /*Place place = Place.builder()
                .latitude(postFormDto.getLocation().get(0))
                .longitude(postFormDto.getLocation().get(1))
                .build();*/
        //place - 위도, 경도 정보 -> retuen placeid
        //post의 place에 placeid 저장

        //Member member = memberRepository.findById(postFormDto.getMemberId())


        //TODO postPlace 별점 계산 로직

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
                .place(null)
                .date(null)
                .build()
                ;

        return postRepository.save(post);
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
        //TODO postPlace 계산
        postRepository.deleteById(postId);
    }
}
