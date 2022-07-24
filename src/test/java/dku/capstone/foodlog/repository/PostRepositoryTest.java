package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.constant.FoodPurpose;
import dku.capstone.foodlog.constant.FoodType;
import dku.capstone.foodlog.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("게시물 저장 테스트")
    public void createPostTest(){
        Post post = Post.builder()
                .member(null)
                .pictureList(null)
                .rating(null)
                .review("review")
                .type(FoodType.ASIAN)
                .purpose(FoodPurpose.FAMILY)
                .place(null)
                .date(null)
                .build();

        Post savedPost = postRepository.save(post);
        System.out.println(savedPost.toString());
    }

}