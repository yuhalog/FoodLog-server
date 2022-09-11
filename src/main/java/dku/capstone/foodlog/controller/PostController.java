package dku.capstone.foodlog.controller;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.PostDto;
import dku.capstone.foodlog.service.AwsS3Service;
import dku.capstone.foodlog.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"Post"})
@Slf4j 
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
public class PostController {

    private final PostService postService;
    private final AwsS3Service awsS3Service;

    @ApiOperation(value = "게시물 생성", notes = "게시물 생성", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto.Response> newPost(
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
            @RequestPart(value = "file") List<MultipartFile> multipartFile,
            @Parameter(name = "model", required = true, schema = @Schema(implementation = PostDto.Request.class), description = "User Details") @RequestPart(value = "post") PostDto.Request postRequest){

        List<String> pictureUrlList = awsS3Service.uploadImage(multipartFile);
        PostDto.Response postResponse = postService.createPost(member, postRequest, pictureUrlList);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

//    @ApiOperation(value = "", notes = "게시물 단일 조회")
//    @GetMapping("/{postId}")
//    public ResponseEntity<PostFormDto> getPost(@PathVariable Long postId){
//        PostFormDto postFormDto = postService.getPost(postId);
//        return new ResponseEntity<>(postFormDto, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "", notes = "게시물 수정")
//    @PutMapping("/{postId}")
//    public ResponseEntity<String> updatePost(@PathVariable("postId") Long postId, @RequestBody PostReviewOnly postReviewOnly) {
//        String review = postService.updatePostReview(postReviewOnly, postId);
//        return new ResponseEntity<>(review, HttpStatus.OK);
//
//    }
//
//    @ApiOperation(value = "", notes = "게시물 삭제")
//    @DeleteMapping("/{postId}")
//    public String deletePost(@PathVariable Long postId){
//        postService.deletePost(postId);
//        return "ok";
//    }
//

}

