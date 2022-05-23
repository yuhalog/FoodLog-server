package dku.capstone.foodlog.api;

import dku.capstone.foodlog.dto.request.SaveOrUpdateProfileRequest;
import dku.capstone.foodlog.dto.response.CreateMemberProfileResponse;
import dku.capstone.foodlog.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @ApiOperation(value = "", notes = "프로필 생성 및 수정")
    @PostMapping("/update/{id}")
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
    @PostMapping("/{id}")
    public ResponseEntity<?> getMemberProfile(@PathVariable("id") Long memberId){
        return new ResponseEntity<>(memberService.getProfile(memberId), HttpStatus.OK);
    }
}