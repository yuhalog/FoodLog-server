package dku.capstone.foodlog.controller;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.PageDto;
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
@RequestMapping("/api")
@RestController
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/v1/subscribe")
    public ResponseEntity<Long> subscribe(
            @RequestBody SubscribeRequest request,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        log.info(request.getSubscribeId().toString());
        Long subscribeId = subscribeService.subscribe(request, member);
        return new ResponseEntity<>(subscribeId, HttpStatus.OK);
    }

    @DeleteMapping("/v1/subscribe")
    public ResponseEntity<Long> unSubscribe(
            @RequestBody SubscribeRequest request,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        subscribeService.unSubscribe(request, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/v1/subscribe/follower/{id}")
    public ResponseEntity<PageDto> getFollowerList(
            @PathVariable("id") Long memberId,
            @PageableDefault(size = 12) Pageable pageable,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        PageDto followerList = subscribeService.getFollowerList(memberId, pageable);
        return new ResponseEntity<>(followerList, HttpStatus.OK);
    }

    @GetMapping("/v1/subscribe/following/{id}")
    public ResponseEntity<Page<MemberPageResponse>> getFollowingList(
            @PathVariable("id") Long memberId,
            @PageableDefault(size = 12) Pageable pageable,
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member) {
        Page<MemberPageResponse> followingList = subscribeService.getFollowingList(memberId, pageable);
        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }
}
