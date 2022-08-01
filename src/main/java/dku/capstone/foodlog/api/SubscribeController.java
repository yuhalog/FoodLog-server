package dku.capstone.foodlog.api;

import dku.capstone.foodlog.dto.request.SubscribeRequest;
import dku.capstone.foodlog.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/api/subscribe")
@RestController
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("")
    public ResponseEntity<Long> subscribe(SubscribeRequest request) {
        Long subscribeId = subscribeService.subscribe(request);
        return new ResponseEntity<>(subscribeId, HttpStatus.OK);
    }

}
