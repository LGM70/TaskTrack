package learn.hui.todolist.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.utils.sign-key}")
    private String signKey;
    @Value("${jwt.utils.expirationMs}")
    private Long expirationMs;

    public String generateJwt(String id) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(signKey)))
                .subject(id)
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .compact();
    }

    public String parseJwt(String jwt) throws Exception {
        return (String) Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(signKey)))
                .build()
                .parseSignedClaims(jwt)
                .getPayload().get("sub");
    }
}
