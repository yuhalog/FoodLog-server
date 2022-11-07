package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.*;
import dku.capstone.foodlog.dto.PageDto;
import dku.capstone.foodlog.dto.PostDto;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        checkDeletePlace(post);
        member.getPostList().remove(post);
        postPictureService.deletePostPictureList(post.getPictureList());
        postRepository.delete(post);
    }

    private void checkDeletePlace(Post post) {
        if (post.getPlace().getPostList().size() == 0) {
            placePostService.deletePlacePost(post.getPlace().getPlacePost());
            placeService.deletePlace(post.getPlace());
            return;
        }
        placePostService.removePlacePost(post.getPlace().getPlacePost());
    }

    public PageDto getSubscriberPosts(Member member, Pageable pageable) {
        Page<Post> subscribePosts = postRepository.getPageSubscribePosts(member, pageable);
        Function<Post,PostDto.Response> fn = (entity -> PostDto.Response.entityToDto(entity));

        return new PageDto(subscribePosts, fn);
    }

    public List<PostDto.Summary> getPostsByMemberAndPlace(Long memberId, Long placeId) {
        List<Post> posts = postRepository.getPostsByMemberAndPlace(memberId, placeId);
        List<PostDto.Summary> postsList = posts.stream().map(PostDto.Summary::entityToDto).collect(Collectors.toList());

        return postsList;
    }
}
