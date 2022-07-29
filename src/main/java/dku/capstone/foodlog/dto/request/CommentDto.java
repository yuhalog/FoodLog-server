package dku.capstone.foodlog.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentDto {

    private Long memberId;

    private String content;

}
