package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.request.PostReviewOnly;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    //create post
    public Post createPost(Post post){
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
        postRepository.deleteById(postId);
    }
}
