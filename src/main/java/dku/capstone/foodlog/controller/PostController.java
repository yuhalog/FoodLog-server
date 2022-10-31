package dku.capstone.foodlog.controller;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.PageDto;
import dku.capstone.foodlog.dto.PostDto;
import dku.capstone.foodlog.service.AwsS3Service;
import dku.capstone.foodlog.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/api")
@RestController
public class PostController {

    private final PostService postService;
    private final AwsS3Service awsS3Service;

    @ApiOperation(value = "게시물 생성", notes = "게시물 생성", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "/v1/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto.Response> createPost(
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
            @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile,
            @Parameter(name = "model", required = false, schema = @Schema(implementation = PostDto.Request.class), description = "User Details") @RequestPart(value = "post", required = false) PostDto.Request postRequest) {

        List<String> pictureUrlList = awsS3Service.uploadImage(multipartFile);
        PostDto.Response postResponse = postService.createPost(member, postRequest, pictureUrlList);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 수정", notes = "게시물 수정 \n 리뷰만 수정 가능합니다.")
    @PutMapping("/v1/post/{postId}")
    public ResponseEntity<PostDto.Response> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostDto.Request postRequest) {
        PostDto.Response postResponse = postService.updatePost(postId, postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 조회", notes = "게시물 조회")
    @GetMapping("/v1/post/{postId}")
    public ResponseEntity<PostDto.Response> getPost(
            @PathVariable("postId") Long postId) {
        PostDto.Response postResponse = postService.getPost(postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 삭제", notes = "게시물 삭제")
    @DeleteMapping("/v1/post/{postId}")
    public ResponseEntity<?> deletePost(
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
            @PathVariable("postId") Long postId) {
        postService.deletePost(member, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/v1/post/subscriber")
    public ResponseEntity<?> recommendPost(
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
            @PageableDefault(size=10, sort = "placeId", direction = Sort.Direction.DESC) Pageable pageable) {
        PageDto subscriberPosts = postService.getSubscriberPosts(member, pageable);

        return new ResponseEntity<>(subscriberPosts, HttpStatus.OK);
    }

    @GetMapping("/v1/posts")
    public ResponseEntity<List<PostDto.Summary>> getPostsListByMemberAndPlace(
        @RequestParam Long memberId,
        @RequestParam Long placeId) {
        List<PostDto.Summary> postsByMember = postService.getPostsByMemberAndPlace(memberId, placeId);

        return new ResponseEntity<>(postsByMember, HttpStatus.OK);
    }
}

