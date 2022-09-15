package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Comment;
import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.CommentDto;
import dku.capstone.foodlog.repository.CommentRepository;
import dku.capstone.foodlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private Post findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시물이 없습니다."));
        return post;
    }

    @Transactional
    public CommentDto.Response createComment(Long postId, CommentDto.Request commentRequest, Member member){
        Post post = findPostById(postId);
        Comment savedComment = saveComment(commentRequest, post, member);
        return new CommentDto.Response(savedComment);
    }

    private Comment saveComment(CommentDto.Request commentRequest, Post post, Member member) {
        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content(commentRequest.getComment())
                .build();

        post.getCommentList().add(comment);

        return commentRepository.save(comment);

    }

    @Transactional
    public void deleteComment(Long postId, Long commentId){
        Post post = findPostById(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("댓글이 없습니다."));

        post.getCommentList().remove(comment);
        commentRepository.delete(comment);
    }
}
