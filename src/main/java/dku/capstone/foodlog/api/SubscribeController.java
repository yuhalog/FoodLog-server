package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.SubscribeRequest;
import dku.capstone.foodlog.dto.response.MemberPageResponse;
import dku.capstone.foodlog.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscribe")
@RestController
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("")
    public ResponseEntity<Long> subscribe(
            @RequestBody SubscribeRequest request,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        log.info(request.getSubscribeId().toString());
        Long subscribeId = subscribeService.subscribe(request, member);
        return new ResponseEntity<>(subscribeId, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Long> unSubscribe(
            @RequestBody SubscribeRequest request,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        subscribeService.unSubscribe(request, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/follower/{id}")
    public ResponseEntity<Page<MemberPageResponse>> getFollowerList(
            @PathVariable("id") Long memberId,
            @PageableDefault(size = 12) Pageable pageable,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        Page<MemberPageResponse> followerList = subscribeService.getFollowerList(memberId, pageable);
        return new ResponseEntity<>(followerList, HttpStatus.OK);
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<Page<MemberPageResponse>> getFollowingList(
            @PathVariable("id") Long memberId,
            @PageableDefault(size = 12) Pageable pageable,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        Page<MemberPageResponse> followingList = subscribeService.getFollowingList(memberId, pageable);
        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }
}
