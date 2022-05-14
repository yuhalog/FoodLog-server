package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.CreateMemberProfileRequest;
import dku.capstone.foodlog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long createMemberProfile(Long memberId, CreateMemberProfileRequest request) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        member.createMemberProfile(request.getUsername(), request.getGender(), request.getBirthday(),
                request.getProfilePicture(), request.getSelfBio());

        return member.getId();
    }

}
