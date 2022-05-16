package dku.capstone.foodlog.service;

import dku.capstone.foodlog.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtUtils jwtUtils;

    public Long getIdByJwt() {
        String token = jwtUtils.getJwtByHeader();;
        if (jwtUtils.isJwtExpired(token)) {
            throw new JwtException("token expired");
        }
        return jwtUtils.getClaimByJwt(token).get("memberId", Long.class);
    }
}
