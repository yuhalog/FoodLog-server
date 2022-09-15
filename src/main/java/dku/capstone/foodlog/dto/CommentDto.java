package dku.capstone.foodlog.dto;

import dku.capstone.foodlog.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommentDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Request {
        private String comment;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response {

        private Long commentId;

        private String memberProfileImage;

        private String username;

        private String comment;

        private String createdDate;

        public Response(Comment comment) {
            this.commentId = comment.getId();
            this.memberProfileImage = comment.getMember().getProfilePicture();
            this.username = comment.getMember().getUsername();
            this.comment = comment.getContent();
            this.createdDate = comment.getCreatedDate().toString();
        }

        public static Response entityToDto(Comment comment) {
            return Response.builder()
                    .commentId(comment.getId())
                    .memberProfileImage(comment.getMember().getProfilePicture())
                    .username(comment.getMember().getUsername())
                    .comment(comment.getContent())
                    .createdDate(comment.getCreatedDate().toString())
                    .build();
        }
    }
}
