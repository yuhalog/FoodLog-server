package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Comment;
import dku.capstone.foodlog.dto.request.CommentDto;
import dku.capstone.foodlog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/comment")
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/{postId}/new")
    public CommentDto newComment(@PathVariable Long postId, @RequestPart CommentDto commentDto){
        CommentDto comment = commentService.createComment(postId, commentDto);
        return comment;
    }

    @DeleteMapping("/{postId}/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        commentService.deleteComment(postId, commentId);
    }
}
