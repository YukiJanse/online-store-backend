package se.jensen.yuki.onlinestorebackend.security.service;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    private final long expirationMs;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JwtService(
            @Value("${jwt.private-key}") String privateKey,
            @Value("${jwt.public-key}") String publicKey,
            @Value("${jwt.expiration-ms}") long expirationMs
    ) throws Exception {
        this.privateKey = loadPrivateKeyFromString(privateKey);
        this.publicKey = loadPublicKeyFromString(publicKey);
        this.expirationMs = expirationMs;
    }

    public String generateAccessToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Long extractUserId(String jwt) {
        return Long.valueOf(parseClaims(jwt).getSubject());
    }

    public boolean validateToken(String jwt) {
        try {
            parseClaims(jwt);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims parseClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private PrivateKey loadPrivateKeyFromString(String pem) throws Exception {
        // Format the argument value
        String key = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(pem);

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private PublicKey loadPublicKeyFromString(String pem) throws Exception {
        String key = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);

        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
    }
}
