package dku.capstone.foodlog.service;

import dku.capstone.foodlog.constant.Gender;
import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.CreateMemberProfileRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired OAuthService oAuthService;

    @Rollback(value = false)
    @Transactional
    @Test
    public void 회원프로필_등록() throws Exception {
        //given

        Member member = Member.builder()
                .email("test@gmail.com")
                .build();

        Long memberId = oAuthService.join(member);

        //when

        String username = "test1";

        Gender gender = Gender.FEMALE;

        String birthdayString = "19990930";
        LocalDate birthday = LocalDate.parse("19990930", DateTimeFormatter.ofPattern("yyyyMMdd"));

        String profilePicture = "http://test.jpg";

        String selfBio = "hello! this is test";

        CreateMemberProfileRequest request = CreateMemberProfileRequest.builder()
                .username(username)
                .gender(gender)
                .birthday(birthday)
                .profilePicture(profilePicture)
                .selfBio(selfBio)
                .build();

        Long createMemberId = memberService.createMemberProfile(memberId, request);

        //then
        assertEquals(memberId, createMemberId);
    }
}