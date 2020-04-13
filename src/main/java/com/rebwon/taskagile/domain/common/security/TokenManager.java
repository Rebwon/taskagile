package com.rebwon.taskagile.domain.common.security;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rebwon.taskagile.domain.model.user.UserId;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenManager {
  private Key secretKey;

  public TokenManager(@Value("${app.token-secret-key}") String secretKey) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
  }

  public String jwt(UserId userId) {
    return Jwts.builder()
      .setSubject(String.valueOf(userId.value()))
      .signWith(secretKey).compact();
  }

  public UserId verifyJwt(String jws) {
    String userIdValue = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jws).getBody().getSubject();
    return new UserId(Long.valueOf(userIdValue));
  }
}
