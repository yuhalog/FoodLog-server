package dku.capstone.foodlog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.LoginRequest;
import dku.capstone.foodlog.dto.request.MemberJoinRequest;
import dku.capstone.foodlog.dto.response.MemberPageResponse;
import dku.capstone.foodlog.dto.response.LoginResponse;
import dku.capstone.foodlog.service.MemberService;
import io.swagger.annotations.ApiOperation;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Slf4j
@RequestMapping(value = "/api", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final ObjectMapper objectMapper;

    @ApiOperation(value = "", notes = "로그인")
    @PostMapping("/v1/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        LoginResponse response = memberService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "회원가입")
    @PostMapping(value = "/v1/join")
    public ResponseEntity<LoginResponse> join(
            @RequestPart(value = "dto", required = false) String memberInfo,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile)
            throws JsonProcessingException, UnsupportedEncodingException {
        String decodeMemberInfo = URLDecoder.decode(memberInfo, "UTF-8");
        MemberJoinRequest request = objectMapper.readValue(decodeMemberInfo, MemberJoinRequest.class);

        if (multipartFile != null) {
            String pictureUrl = memberService.createProfilePicture(multipartFile);
            request.setProfilePicture(pictureUrl);
        }
        LoginResponse response = memberService.join(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "username 중복 체크")
    @PostMapping("/v1/member/username")
    public ResponseEntity<?> isUsernameDuplicate(@RequestBody String username) {
        return new ResponseEntity<>(memberService.isUsernameDuplicate(username), HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "프로필 수정")
    @PutMapping("/v1/member/profile")
    public ResponseEntity<?> createMemberProfile(
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
            @RequestPart(value = "member") String memberInfo,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile)
            throws JsonProcessingException, UnsupportedEncodingException {
        String decodeMemberInfo = URLDecoder.decode(memberInfo, "UTF-8");
        MemberJoinRequest request = objectMapper.readValue(decodeMemberInfo, MemberJoinRequest.class);
        Long response = null;
        try {
            if (multipartFile != null) {
                String pictureUrl = memberService.uploadProfilePicture(member, multipartFile);
                request.setProfilePicture(pictureUrl);
            } else {
                request.setProfilePicture(member.getProfilePicture());
            }
            response = memberService.updateProfile(member, request);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "프로필 조회")
    @GetMapping("/v1/member/profile/{id}")
    public ResponseEntity<?> getMemberProfile(
            @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
            @PathVariable("id") Long memberId){
        return new ResponseEntity<>(memberService.getProfile(memberId, member), HttpStatus.OK);
    }

    @GetMapping("/v1/member/list")
    public ResponseEntity<Page<MemberPageResponse>> getMemberList(
            @RequestParam String username,
            @PageableDefault(size=10, sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MemberPageResponse> memberList = memberService.getMemberPageByUsername(username, pageable);
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }
}