package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Comment;
import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.request.CommentDto;
import dku.capstone.foodlog.repository.CommentRepository;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    public CommentDto createComment(Long postId, CommentDto commentDto){
        Post post = postRepository.findById(postId).get();
        Member member = memberRepository.findById(commentDto.getMemberId()).get();
        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content(commentDto.getContent())
                .build();

        post.getCommentList().add(comment);
        postRepository.save(post);

        commentRepository.save(comment);

        return commentDto;
    }

    public void deleteComment(Long postId, Long commentId){
        Post post = postRepository.findById(postId).get();
        Comment comment = commentRepository.findById(commentId).get();

        post.getCommentList().remove(comment);
        postRepository.save(post);

        commentRepository.deleteById(commentId);
    }
}
