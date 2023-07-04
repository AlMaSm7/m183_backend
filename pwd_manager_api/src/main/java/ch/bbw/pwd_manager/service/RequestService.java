package ch.bbw.pwd_manager.service;

import ch.bbw.pwd_manager.config.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestService {
    private final JwtService jwtService;

    public String extractTokenFromRequest(HttpServletRequest request) {
        String token = null;
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        return token;
    }

    public Long getUserId(String token) {
        Claims claims = jwtService.extractAllClaims(token);
        Integer userIdInteger = (Integer) claims.get("userId");
        Long userId = userIdInteger.longValue();
        System.out.println(userId);
        return userId;
    }
}
