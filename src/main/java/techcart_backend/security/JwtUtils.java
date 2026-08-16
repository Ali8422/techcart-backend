package techcart_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // 1. Generates a cryptographically secure 256-bit secret key for HMAC-SHA256
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Generates a signed JWT token for an authenticated user.
     *
     * @param email The authenticated user's email address (used as Subject)
     * @return Compact Base64URL-encoded JWT string
     */
    public String generateToken(String email) {
        Date now = new Date();
        // 2. Token Validity Duration: 24 hours (86,400,000 milliseconds)
        long jwtExpirationMs = 86400000;
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(email)                 // Sets 'sub' claim
                .setIssuedAt(now)                  // Sets 'iat' claim
                .setExpiration(expiryDate)         // Sets 'exp' claim
                .signWith(key)                     // Computes cryptographic signature
                .compact();                        // Assembles Header.Payload.Signature
    }

    /**
     * Extracts the user email (subject) from a validated token payload.
     *
     * @param token The incoming Base64URL-encoded JWT
     * @return User's email address string
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)               // Provides key to recalculate signature
                .build()
                .parseClaimsJws(token)            // Parses and verifies signature/expiration
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validates an incoming token against tampering and expiration.
     *
     * @param token Incoming JWT string
     * @return true if valid signature and unexpired; false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Thrown if signature is invalid, token expired, or malformed
            return false;
        }
    }
}