package pl.zgora.uz.wiea.pkdg.config.jwt;

import io.jsonwebtoken.Jwts;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenUtilTest {

    private static final String JWT_SECRET = "secret";
    private static final String JWT_ISSUER = "pkdg";
    private static final int JWT_EXPIRATION_MS = 100000;

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil(JWT_SECRET, JWT_ISSUER, JWT_EXPIRATION_MS);
    }

    @Test
    void shouldGenerateAccessToken() {
        // Given
        val username = "username";

        // When
        val token = jwtTokenUtil.generateAccessToken(username);

        // Then
        assertThat(token).isNotNull();

        val claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        assertThat(claims).isNotNull();
        assertThat(claims.getSubject()).isEqualTo(username);
        assertThat(claims.getIssuer()).isEqualTo(JWT_ISSUER);
    }

    @Test
    void shouldGetUsernameFromTokenClaims() {
        // Given
        val username = "username";
        val token = jwtTokenUtil.generateAccessToken(username);

        // When
        val usernameFromToken = jwtTokenUtil.getUsername(token);

        // Then
        assertThat(usernameFromToken).isNotNull().isEqualTo(username);
    }
}
