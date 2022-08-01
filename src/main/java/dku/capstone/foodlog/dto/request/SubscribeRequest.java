package dku.capstone.foodlog.dto.request;

import lombok.Data;

@Data
public class SubscribeRequest {

    private Long memberId;
    private Long subscribeId;
}
