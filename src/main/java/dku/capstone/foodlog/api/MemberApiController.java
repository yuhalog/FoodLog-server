package dku.capstone.foodlog.api;

import dku.capstone.foodlog.dto.request.CreateMemberProfileRequest;
import dku.capstone.foodlog.dto.response.CreateMemberProfileResponse;
import dku.capstone.foodlog.service.JwtService;
import dku.capstone.foodlog.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @ApiOperation(value = "", notes = "프로필 생성")
    @PostMapping("/api/member/create/{id}")
    public ResponseEntity<CreateMemberProfileResponse> createMemberProfile(
            @PathVariable("id") Long memberId,
            @RequestBody CreateMemberProfileRequest request) {

        CreateMemberProfileResponse response = null;

        try {

            Long memberIdByJwt = jwtService.getIdByJwt();
            if (memberIdByJwt != memberId) {
                return ResponseEntity.notFound().build();
            }

            Long createMemberId = memberService.createMemberProfile(memberId, request);

            response = CreateMemberProfileResponse.builder()
                    .id(createMemberId)
                    .build();

        } catch (NoSuchElementException e) {
            log.info("");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}