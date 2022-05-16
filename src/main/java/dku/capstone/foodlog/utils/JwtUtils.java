package dku.capstone.foodlog.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private String secretKey;

    /**
     * 토큰 발급
     * @param memberId
     * @return String
     */
    public String createJwtToken(Long memberId) {
        Date now = new Date();
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("food log")
                .claim("memberId", memberId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 헤더에서 토큰값 가져오기
     * @return Jwt
     */
    public String getJwtByHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request.getHeader("ACCESS-TOKEN");
    }

    /**
     * 토큰에서 정보 가져오기
     * @return
     */
    public Claims getClaimByJwt(String token) {
        if(token == null || token.length() == 0) {
            throw new JwtException("invalid");
        }

        Jws<Claims> claims;

        try{
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
        } catch (Exception ignored) {
            throw new JwtException("invalid!");
        }

        return claims.getBody();
    }

    /**
     * jwt 만료 여부
     * @param token
     * @return
     */
    public Boolean isJwtExpired(String token) {
        return getClaimByJwt(token).getExpiration().before(new Date());
    }
}
