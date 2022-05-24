package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(Post post){
        return postRepository.save(post);
    }
}
