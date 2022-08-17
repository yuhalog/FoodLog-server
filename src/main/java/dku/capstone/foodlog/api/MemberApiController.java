package dku.capstone.foodlog.api;

import dku.capstone.foodlog.dto.request.LoginRequest;
import dku.capstone.foodlog.dto.request.MemberJoinRequest;
import dku.capstone.foodlog.dto.response.MemberPageResponse;
import dku.capstone.foodlog.dto.response.MemberProfileDto;
import dku.capstone.foodlog.dto.response.LoginResponse;
import dku.capstone.foodlog.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Slf4j
@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @ApiOperation(value = "", notes = "로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        LoginResponse response = memberService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<LoginResponse> join(
            @RequestPart(value = "memberJoinDto") MemberJoinRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile multipartFile) {

        if (multipartFile != null) {
            String pictureUrl = memberService.createProfilePicture(multipartFile);
            request.setProfilePicture(pictureUrl);
        }
        LoginResponse response = memberService.join(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "프로필 수정")
    @PutMapping("/profile/{id}")
    public ResponseEntity<?> createMemberProfile(
            @PathVariable("id") Long memberId,
            @RequestPart(value = "profileDto") MemberProfileDto request,
            @RequestPart(value = "profileImage", required = false) MultipartFile multipartFile) {
        Long response = null;

        try {
            if (multipartFile != null) {
                String pictureUrl = memberService.uploadProfilePicture(memberId, multipartFile);
                request.setProfilePicture(pictureUrl);
            }

            response = memberService.updateProfile(memberId, request);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "username 중복 체크")
    @PostMapping("/check/username")
    public ResponseEntity<?> isUsernameDuplicate(@RequestBody String username) {
        return new ResponseEntity<>(memberService.isUsernameDuplicate(username), HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "프로필 조회")
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getMemberProfile(@PathVariable("id") Long memberId){
        return new ResponseEntity<>(memberService.getProfile(memberId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<MemberPageResponse>> getMemberList(
            @RequestParam String username,
            @PageableDefault(size=10, sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MemberPageResponse> memberList = memberService.getMemberPageByUsername(username, pageable);
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }
}