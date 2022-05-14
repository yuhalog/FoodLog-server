package dku.capstone.foodlog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Data
public class MemberDto {

    private String jwtToken;
    private Long memberId;
    private String email;
}
