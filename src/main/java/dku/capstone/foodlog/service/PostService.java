package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public Optional<Post> seePost(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        return post;
    }
    public String editPost(Post post){
        return "";
    }
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }
}
