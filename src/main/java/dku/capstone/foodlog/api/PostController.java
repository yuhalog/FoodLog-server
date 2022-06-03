package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.request.PostFormDto;
import dku.capstone.foodlog.dto.request.PostReviewOnly;
import dku.capstone.foodlog.dto.response.ApiResponse;
import dku.capstone.foodlog.repository.PostRepository;
import dku.capstone.foodlog.service.AwsS3Service;
import dku.capstone.foodlog.service.PostService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AwsS3Service awsS3Service;

    @PostMapping("/new")
    public PostFormDto newPost(@RequestPart PostFormDto postFormDto, @RequestPart List<MultipartFile> multipartFile){

        List<String> pictureImgList = awsS3Service.uploadImage(multipartFile);
        Post post = postService.createPost(postFormDto, pictureImgList);

        log.info("rating={}, review={}, type={}, purpose={}, date={}",
                //postFormDto.getPictureList(),
                postFormDto.getRating(),
                postFormDto.getReview(),
                postFormDto.getType(),
                postFormDto.getPurpose(),
                //postFormDto.getLocation(),
                postFormDto.getDate());

        return postFormDto;
    }

    @GetMapping("/{postId}")
    public String seePost(@PathVariable Long postId){
        Post post = postService.seePost(postId);
        return "ok";
    }

    @PatchMapping("/post/{postId}/edit")
    public String editPost(@PathVariable Long postId, @RequestBody PostReviewOnly postReviewOnly) {
        postService.editPost(postReviewOnly, postId);
        return "ok";

    }

    @DeleteMapping("/post/{postId}/delete")
    public String deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return "ok";
    }
}
