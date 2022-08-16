package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Subscribe;
import dku.capstone.foodlog.dto.request.SubscribeRequest;
import dku.capstone.foodlog.dto.response.MemberPageResponse;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional
@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final MemberRepository memberRepository;
    private final SubscribeRepository subscribeRepository;

    private Member getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));
        return member;
    }

    /**
     * 구독
     */
    public Long subscribe(SubscribeRequest request, Member member) {
        Long subscribeId = request.getSubscribeId();

        Member subscriber = memberRepository.findById(subscribeId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        Subscribe subscribe = Subscribe.builder()
                .member(member)
                .subscriber(subscriber)
                .build();

        Subscribe saveSubscribe = subscribeRepository.save(subscribe);

        return saveSubscribe.getId();
    }

    /**
     * 구독 취소
     */
    public void unSubscribe(SubscribeRequest request, Member member) {
        Member subscriber = memberRepository.findById(request.getSubscribeId())
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        Subscribe subscribe = subscribeRepository.findByMemberAndSubscriber(member, subscriber);

        subscribeRepository.delete(subscribe);
//        member.getSubscribers().remove(subscribe);
    }

    public Page<MemberPageResponse> getFollowerList (Long memberId, Pageable pageable) {
        Member member = getMemberById(memberId);
        Page<Subscribe> subscribers = subscribeRepository.findBySubscriber(member, pageable);
        Page<MemberPageResponse> followerList = subscribers.map(entity -> new MemberPageResponse(entity));

        return followerList;
    }

    public Page<MemberPageResponse> getFollowingList (Long memberId, Pageable pageable) {
        Member member = getMemberById(memberId);
        Page<Subscribe> subscribers = subscribeRepository.findByMember(member, pageable);
        Page<MemberPageResponse> followingList = subscribers.map(entity -> new MemberPageResponse(entity));

        return followingList;
    }
}
