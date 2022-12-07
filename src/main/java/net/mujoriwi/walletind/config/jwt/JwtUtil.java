package net.mujoriwi.walletind.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import net.mujoriwi.walletind.service.serviceimpl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
  @Value("${jwt.secret.key}")
  private String JWT_SECRET_KEY;

  @Value("${jwt.expiration.ms}")
  private Long JWT_EXPIRATION_MS;

  // method untuk generate jwt token berdasarkan user yang telah diautentikasi
  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    String base64Secret = Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());

    return JWT.create()
        .withSubject("login")
        .withClaim("email", userPrincipal.getUsername())
        .withIssuedAt(new Date())
        .withExpiresAt(new Date((new Date()).getTime() + JWT_EXPIRATION_MS))
        .withIssuer("api-library")
        .sign(Algorithm.HMAC256(base64Secret));
  }

  // method untuk mendapatkan username/email dari subject jwt token
  public String getUsernameFromToken(String jwtToken) {
    String base64Secret = Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());

    return JWT.require(Algorithm.HMAC256(base64Secret))
        .build()
        .verify(jwtToken)
        .getClaims().get("email").asString();
  }

  // method untuk validate jwt token
  public boolean validateJwtToken(String jwtToken) {
    String base64Secret = Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());

    JWT.require(Algorithm.HMAC256(base64Secret)).build().verify(jwtToken);
    return true;
  }
}
