package com.kanboo.www.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;


@Service
public class JwtSecurityService {
    private static final String SECRET_KEY = "ashjkdlehfaljksdhfcxbnakljshedfnjklasmehasdfasdfasdfsdafasdfasdfasdfasdfasdfsdafsdafsdafsdafasdfsdafasdfsdfsdffkljahsdkljfhasdkljfhalsjkdfh";

    public String createToken(String memTag, Long expTime) {
        if(expTime <= 0) {
            throw new RuntimeException("Expired Time must exceed zero");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setSubject(memTag)
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    public String getToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
        return claims.getSubject();
    }
}
