package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.request.PostFormDto;
import dku.capstone.foodlog.service.PostService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController("post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/new")
    public PostFormDto newPost(@RequestBody PostFormDto postFormDto){

        Post post = Post.builder()
                .member(null) //.member(memberRepository.findByUsername(postFormDto.getUsername()))
                .pictureList(null)
                .rating(postFormDto.getRating())
                .review(postFormDto.getReview())
                .type(postFormDto.getType())
                .purpose(postFormDto.getPurpose())
                .place(null)
                .date(null)
                .build()
                ;

        postService.createPost(post);

        log.info("pictureList={}, rating={}, review={}, type={}, purpose={}, place={}, date={}",
                postFormDto.getPictureList(),
                postFormDto.getRating(),
                postFormDto.getReview(),
                postFormDto.getType(),
                postFormDto.getPurpose(),
                postFormDto.getPlace(),
                postFormDto.getDate());

        return postFormDto;
    }
    @GetMapping("/{postId}")
    public String seePost(@PathVariable String postId){
        return "ok";
    }

    @PatchMapping("/{postId}/edit")
    public String editPost(@RequestBody PostFormDto postFormDto){
        return "ok";
    }

    @DeleteMapping("/{postId}/delete")
    public String deletePost(@RequestBody PostFormDto postFormDto){
        return "ok";
    }
}
