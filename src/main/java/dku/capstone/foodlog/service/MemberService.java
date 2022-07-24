package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.LoginRequest;
import dku.capstone.foodlog.dto.response.MemberProfileDto;
import dku.capstone.foodlog.dto.response.LoginResponse;
import dku.capstone.foodlog.dto.response.MemberDto;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final AwsS3Service awsS3Service;

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
    public Long updateProfile(Long memberId, MemberProfileDto request) throws Exception {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));
        if ((member.getUsername().equals(request.getUsername())) || (!isUsernameDuplicate(request.getUsername()))) {
            member.updateProfile(request);
            return member.getId();
        } else {
            throw new Exception("중복 확인을 해주세요!");
        }
    }

    /**
     * username 중복 체크
     */
    public boolean isUsernameDuplicate(String username){
        List<Member> member = memberRepository.findAllByUsername(username);
        if (member.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 프로필 사진 등록
     */
    public String uploadProfilePicture(Long memberId, MultipartFile multipartFile) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        if (member.getProfilePicture()!=null) {
            String pictureUrl = member.getProfilePicture();
            String pictureName = pictureUrl.substring(55);
            awsS3Service.deleteImage(pictureName);
        }
        List<MultipartFile> picture = new ArrayList<>();
        picture.add(multipartFile);
        List<String> pictureUrl = awsS3Service.uploadImage(picture);

        return pictureUrl.get(0);
    }
}
