package pl.zgora.uz.wiea.pkdg.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.issuer}")
	private String jwtIssuer;

	@Value("${jwt.expiration-ms}")
	private int jwtExpirationMs;

	public String generateAccessToken(final String username) {
		return Jwts.builder()
				.setSubject(String.format("%s", username))
				.setIssuer(jwtIssuer)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}

	public boolean validate(String token) {
		Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		return true;
	}
}
