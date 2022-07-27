package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.request.PostFormDto;
import dku.capstone.foodlog.dto.request.PostReviewOnly;
import dku.capstone.foodlog.dto.response.CursorResult;
import dku.capstone.foodlog.service.AwsS3Service;
import dku.capstone.foodlog.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {

    private static final int DEFAULT_SIZE = 10;

    private final PostService postService;
    private final AwsS3Service awsS3Service;

    @ApiOperation(value = "", notes = "게시물 생성")
    @PostMapping("")
    public ResponseEntity<PostFormDto> newPost(@RequestPart(value = "postFormDto") PostFormDto postFormDto,
                                  @RequestPart(value = "postPictureFile") List<MultipartFile> multipartFile){

        List<String> pictureImgList = awsS3Service.uploadImage(multipartFile);
        PostFormDto newPost = postService.createPost(postFormDto, pictureImgList);

        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "게시물 단일 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostFormDto> getPost(@PathVariable Long postId){
        PostFormDto postFormDto = postService.getPost(postId);
        return new ResponseEntity<>(postFormDto, HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "게시물 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable("postId") Long postId, @RequestBody PostReviewOnly postReviewOnly) {
        String review = postService.updatePostReview(postReviewOnly, postId);
        return new ResponseEntity<>(review, HttpStatus.OK);

    }

    @ApiOperation(value = "", notes = "게시물 삭제")
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return "ok";
    }

    @GetMapping
    public CursorResult<Post> getPosts(Long cursorId, Integer size) {
        if (size == null) size = DEFAULT_SIZE;
        return this.postService.get(cursorId, PageRequest.of(0, size));
    }

}

