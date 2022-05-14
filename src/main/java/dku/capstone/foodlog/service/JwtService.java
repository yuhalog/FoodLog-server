package dku.capstone.foodlog.service;

import dku.capstone.foodlog.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtUtils jwtUtils;

    public Long getIdByJwt() {
        return jwtUtils.gwtJwt().get("memberId", Long.class);
    }
}
