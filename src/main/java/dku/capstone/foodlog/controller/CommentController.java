package dku.capstone.foodlog.controller;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.CommentDto;
import dku.capstone.foodlog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/v1/post/{postId}/comment")
    public ResponseEntity<CommentDto.Response> newComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentDto.Request commentRequest,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        CommentDto.Response commentResponse = commentService.createComment(postId, commentRequest, member);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @DeleteMapping("/v1/post/{postId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
