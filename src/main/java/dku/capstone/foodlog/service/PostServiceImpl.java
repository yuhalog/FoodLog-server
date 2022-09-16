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

    private Post findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시물이 없습니다."));
        return post;
    }

    @Transactional
    public PostDto.Response createPost(Member member, PostDto.Request postRequest, List<String> imageNameList) {

        Place place = placeService.checkPlaceInDb(postRequest);
        Post savedPost = savePost(member, place, postRequest);
        postPictureService.savePostPictureList(imageNameList, savedPost);
        placePostService.addPlacePost(savedPost.getPlace().getPlacePost());

        // TODO 메소드 분리하기
        return new PostDto.Response(savedPost);
    }

    @Transactional
    Post savePost(Member member, Place place, PostDto.Request postRequest) {

        Post newPost = Post.builder()
                .member(member)
                .rating(postRequest.getRating())
                .review(postRequest.getReview())
                .date(LocalDate.parse(postRequest.getDate(), DateTimeFormatter.ISO_DATE))
                .place(place)
                .purpose(postRequest.getPurpose())
                .build();

        place.getPostList().add(newPost);
        member.getPostList().add(newPost);

        return postRepository.save(newPost);
    }

    @Transactional
    public PostDto.Response updatePost(Long postId, PostDto.Request postRequest) {
        Post post = findPostById(postId);
        post.setReview(postRequest.getReview());
        return new PostDto.Response(post);
    }

    public PostDto.Response getPost(Long postId) {
        Post post = findPostById(postId);
        return new PostDto.Response(post);
    }

    @Transactional
    public void deletePost(Member member, Long postId) {
        Post post = findPostById(postId);
        post.getPlace().getPostList().remove(post);
        member.getPostList().remove(post);
        postPictureService.deletePostPictureList(post.getPictureList());
        placePostService.removePlacePost(post.getPlace().getPlacePost());
        postRepository.delete(post);
    }
}