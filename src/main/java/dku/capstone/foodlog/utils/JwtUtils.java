package dku.capstone.foodlog.utils;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private Long validTime = 1000L * 60 * 180;

    /**
     * 토큰 발급
     */
    public String createToken(String email, Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("food log")
                .claim("memberId", memberId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 헤더에서 토큰값 가져오기
     */
    public String getJwtByHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * 토큰에서 정보 가져오기
     */
    public Claims getClaimByJwt(String token) {
        if(token == null || token.length() == 0) {
            throw new JwtException("토큰이 존재하지 않습니다.");
        }
        Jws<Claims> claims;

        try{
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
        } catch (Exception ignored) {
            throw new JwtException("토큰이 유효하지 않습니다.");
        }
        return claims.getBody();
    }

    /**
     * 토큰에서 이메일 정보 얻기
     */
    public String getEmailByToken(String token) {
        if (isTokenExpired(token)) {
            throw new JwtException("token expired");
        }
        return getClaimByJwt(token).getSubject();
    }

    /**
     * 토큰에서 memberId 정보 얻기
     */
    public Long getMemberIdByToken(String token) {
        if (isTokenExpired(token)) {
            throw new JwtException("token expired");
        }
        return getClaimByJwt(token).get("memberId", Long.class);
    }

    /**
     * jwt 만료 여부
     */
    public Boolean isTokenExpired(String token) {
        return getClaimByJwt(token).getExpiration().before(new Date());
    }


}
