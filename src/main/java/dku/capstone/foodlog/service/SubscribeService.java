package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Subscribe;
import dku.capstone.foodlog.dto.request.SubscribeRequest;
import dku.capstone.foodlog.dto.response.SubscribeListResponse;
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
    public Long deleteSubscribe(SubscribeRequest request, Member member) {
        Member subscriber = memberRepository.findById(request.getSubscribeId())
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        Subscribe subscribe = subscribeRepository.findByMemberAndSubscriber(member, subscriber);

        subscribeRepository.delete(subscribe);
        member.getSubscribers().remove(subscribe);

        return subscribe.getId();
    }

    /**
     * 구독 리스트 조회
     */
    public Page<SubscribeListResponse> getSubscribeList (Pageable pageable, Member member) {
        Page<Subscribe> subscribers = subscribeRepository.findByMember(member, pageable);
        Page<SubscribeListResponse> subscribeList = subscribers.map(entity -> new SubscribeListResponse(entity));

        return subscribeList;
    }
}
