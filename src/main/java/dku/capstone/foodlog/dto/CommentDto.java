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

        private String memberProfileImage;

        private String username;

        private String comment;

        private String createdDate;

        public Response(Comment comment) {
            this.memberProfileImage = comment.getMember().getProfilePicture();
            this.username = comment.getMember().getUsername();
            this.comment = comment.getContent();
            this.createdDate = comment.getCreatedDate().toString();
        }
    }
}
