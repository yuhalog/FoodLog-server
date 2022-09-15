package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.CommentDto;

public interface CommentService {

    void deleteComment(Long postId, Long commentId);

    CommentDto.Response createComment(Long postId, CommentDto.Request commentRequest, Member member);
}
