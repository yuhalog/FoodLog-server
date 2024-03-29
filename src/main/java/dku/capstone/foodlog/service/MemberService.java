package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Subscribe;
import dku.capstone.foodlog.dto.request.LoginRequest;
import dku.capstone.foodlog.dto.request.MemberJoinRequest;
import dku.capstone.foodlog.dto.response.MemberPageResponse;
import dku.capstone.foodlog.dto.response.MemberProfileDto;
import dku.capstone.foodlog.dto.response.LoginResponse;
import dku.capstone.foodlog.dto.response.MemberDto;
import dku.capstone.foodlog.repository.MemberRepository;
import dku.capstone.foodlog.repository.SubscribeRepository;
import dku.capstone.foodlog.utils.JwtUtils;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final SubscribeRepository subscribeRepository;
    private final JwtUtils jwtUtils;
    private final AwsS3Service awsS3Service;

    /**
     * 회원가입
     */
    @Transactional
    public LoginResponse join(MemberJoinRequest memberJoinRequest) {

        String email = memberJoinRequest.getEmail();
        String username = memberJoinRequest.getUsername();
        boolean isUsernameDuplicate = isUsernameDuplicate(username);

        if (isUsernameDuplicate) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        Member member = Member.builder()
                .email(email)
                .username(username)
                .birthday(LocalDate.parse(memberJoinRequest.getBirthday()))
                .selfBio(memberJoinRequest.getSelfBio())
                .profilePicture(memberJoinRequest.getProfilePicture())
                .gender(memberJoinRequest.getGender())
                .build();

        memberRepository.save(member);
        String accessToken = jwtUtils.createToken(email, member.getId());

        LoginResponse loginResponse = new LoginResponse(member.getId(), email, accessToken, true);

        return loginResponse;
    }

    /**
     * 로그인
     */
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail();
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            return new LoginResponse(false);
        }
        else {
            Long memberId = member.getId();
            String accessToken = jwtUtils.createToken(member.getEmail(), memberId);

            return new LoginResponse(memberId, email, accessToken, true);
        }
    }

    /**
     * 프로필 설정 - 조회
     */
    public MemberDto getProfile(Long memberId, Member member) {

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));

        Subscribe subscribe = subscribeRepository.findByMemberAndSubscriber(member, findMember);

        boolean isFollowing = false;
        if (subscribe != null) {
            isFollowing = true;
        }

        return new MemberDto(findMember, isFollowing);
    }

    /**
     * 프로필 수정
     */
    @Transactional
    public Long updateProfile(Member member, MemberJoinRequest request) throws Exception {
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
    public String createProfilePicture(MultipartFile multipartFile) {
        return awsS3Service.uploadMemberProfileImage(multipartFile);
    }

    /**
     * 프로필 사진 삭제
     */
    public void deleteProfilePicture(Member member) {
        if (member.getProfilePicture()!=null) {
            String pictureUrl = member.getProfilePicture();
            String pictureName = pictureUrl.substring(55);
            awsS3Service.deleteImage(pictureName);
        }
    }

    /**
     * 프로필 사진 수정
     */
    public String uploadProfilePicture(Member member, MultipartFile multipartFile) {
        deleteProfilePicture(member);
        String profilePicture = createProfilePicture(multipartFile);
        return profilePicture;
    }

    public Page<MemberPageResponse> getMemberPageByUsername(String username, Pageable pageable) {
        Page<Member> members = memberRepository.findAllByUsernameContains(username, pageable);
        Page<MemberPageResponse> memberList = members.map(entity -> new MemberPageResponse(entity));

        return memberList;
    }
}
