package dku.capstone.foodlog.api;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.LoginRequest;
import dku.capstone.foodlog.dto.request.SaveOrUpdateProfileRequest;
import dku.capstone.foodlog.dto.response.CreateMemberProfileResponse;
import dku.capstone.foodlog.dto.response.LoginResponse;
import dku.capstone.foodlog.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "", notes = "프로필 생성 및 수정")
    @PostMapping("/profile/{id}")
    public ResponseEntity<?> createMemberProfile(
            @PathVariable("id") Long memberId,
            @RequestBody SaveOrUpdateProfileRequest request) {


        CreateMemberProfileResponse response = null;

        try {

            memberService.updateProfile(memberId, request);

        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "", notes = "username 중복 체크")
    @PostMapping("/check/username")
    public boolean isUsernameDuplicate(@RequestBody String username) {
        return memberService.isUsernameDuplicate(username);
    }

    @ApiOperation(value = "", notes = "프로필 조회")
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getMemberProfile(@PathVariable("id") Long memberId){
        return new ResponseEntity<>(memberService.getProfile(memberId), HttpStatus.OK);
    }
}