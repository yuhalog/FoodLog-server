package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.SaveOrUpdateProfileRequest;
import dku.capstone.foodlog.dto.response.MemberDto;
import dku.capstone.foodlog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 프로필 설정 - 조회
     */
    public MemberDto getProfile(Long memberId) {

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        return new MemberDto(findMember);
    }

    /**
     * 프로필 등록 및 수정
     */
    @Transactional
    public void saveOrUpdateProfile(Long memberId, SaveOrUpdateProfileRequest request) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        if (!isUsernameDuplicate(member.getUsername())) {
            member.saveOrUpdateProfile(request.getUsername(), request.getGender(), request.getBirthday(),
                    request.getProfilePicture(), request.getSelfBio());
        }
    }

    /**
     * username 중복 체크
     */
    public boolean isUsernameDuplicate(String username){
        Member member = memberRepository.findByUsername(username);

        if (member!=null) {
            return false;
        }
        return true;
    }
}
