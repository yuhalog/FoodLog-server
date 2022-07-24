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
    @PostMapping("/new")
    public ResponseEntity<?> newPost(@RequestPart(value = "postFormDto") PostFormDto postFormDto,
                                  @RequestPart(value = "postPictureFile", required = false) List<MultipartFile> multipartFile){

        List<String> pictureImgList = awsS3Service.uploadImage(multipartFile);
        PostFormDto newPost = postService.createPost(postFormDto, pictureImgList);

        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public String seePost(@PathVariable Long postId){
        Post post = postService.seePost(postId);
        return "ok";
    }

    @PutMapping("/{postId}")
    public String editPost(@PathVariable Long postId, @RequestBody PostReviewOnly postReviewOnly) {
        postService.editPost(postReviewOnly, postId);
        return "ok";

    }

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

