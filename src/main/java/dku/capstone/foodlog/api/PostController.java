package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.request.PostFormDto;
import dku.capstone.foodlog.service.PostService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/post/new")
    public PostFormDto newPost(@RequestBody PostFormDto postFormDto){

        Post post = Post.builder()
                .member(null) //.member(memberRepository.findByUsername(postFormDto.getUsername()))
                .pictureList(postFormDto.getPictureList())
                .review(postFormDto.getReview())
                .type(postFormDto.getType())
                .purpose(postFormDto.getPurpose())
                .place(postFormDto.getPlace())
                .date(postFormDto.getDate())
                .build()
                ;

        postService.savePost(post);

        log.info("rating={}, review={}, type={}, purpose={}",
                //postFormDto.getPictureList(),
                postFormDto.getRating(),
                postFormDto.getReview(),
                postFormDto.getType(),
                postFormDto.getPurpose());
                //postFormDto.getPlace(),
                //postFormDto.getDate());

        return postFormDto;
    }
    @GetMapping("/post/{postId}")
    public String seePost(@RequestBody PostFormDto postFormDto){
        return "ok";
    }

    @PatchMapping("/post/{postId}/edit")
    public String editPost(@RequestBody PostFormDto postFormDto){
        return "ok";
    }

    @DeleteMapping("/post/{postId}/delete")
    public String deletePost(@RequestBody PostFormDto postFormDto){
        return "ok";
    }
}
