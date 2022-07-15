package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.LoginRequest;
import dku.capstone.foodlog.dto.request.SaveOrUpdateProfileRequest;
import dku.capstone.foodlog.dto.response.LoginResponse;
import dku.capstone.foodlog.dto.response.MemberDto;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;

    /**
     * 회원가입
     */
    @Transactional
    public LoginResponse join(LoginRequest request) {
        String email = request.getEmail();

        Member member = Member.builder()
                .email(email)
                .build();

        memberRepository.save(member);
        String token = jwtUtils.createToken(email, member.getId());

        return new LoginResponse(member.getId(), token, false);
    }

    /**
     * 로그인
     */
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail();
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            return join(request);
        }
        else {
            Long memberId = member.getId();
            String token = jwtUtils.createToken(member.getEmail(), memberId);

            return new LoginResponse(memberId, token, true);
        }
    }

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
    public Long updateProfile(Long memberId, SaveOrUpdateProfileRequest request) throws Exception {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        if (!isUsernameDuplicate(member.getUsername())) {
            member.saveOrUpdateProfile(request.getUsername(), request.getGender(), request.getBirthday(),
                    request.getProfilePicture(), request.getSelfBio());
            return member.getId();
        } else {
            throw new Exception("중복 확인을 해주세요!");
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
