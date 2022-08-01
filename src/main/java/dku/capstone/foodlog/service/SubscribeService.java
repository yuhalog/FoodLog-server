package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Subscribe;
import dku.capstone.foodlog.dto.request.SubscribeRequest;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final MemberRepository memberRepository;
    private final SubscribeRepository subscribeRepository;

    /**
     * 구독
     */
    public Long subscribe(SubscribeRequest request) {
        Long memberId = request.getMemberId();
        Long subscribeId = request.getSubscribeId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));
        Member subscriber = memberRepository.findById(subscribeId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        Subscribe subscribe = Subscribe.builder()
                .member(member)
                .subscriber(subscriber)
                .build();

        Subscribe saveSubscribe = subscribeRepository.save(subscribe);

        return saveSubscribe.getId();
    }

    // TODO : 구독 취소, 구독 리스트 조회
}
