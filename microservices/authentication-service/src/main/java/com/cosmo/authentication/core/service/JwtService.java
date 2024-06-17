package com.cosmo.authentication.core.service;

import com.cosmo.authentication.core.constant.JwtTokenConstants;
import com.cosmo.authentication.user.entity.Customer;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${SECRET_KEY}")
    private String key;

    private static String SECRET_KEY;

    @Value("${SECRET_KEY}")
    public void setSecretKeyStatic(String key) {
        JwtService.SECRET_KEY = key;
    }

    public String extractUsername(String token) {
        return extractClaim(token, (Claims::getSubject));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(UserDetails userDetails) {
        return generateToken(getClaims(userDetails), userDetails);
    }

    public String generateToken(JWTClaimsSet extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims.getClaims())
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private JWTClaimsSet getClaims(UserDetails userDetails) {
        JWTClaimsSet.Builder jwtClaimsSet = getCommonClaims(userDetails);
//        if (isTwoFactorEnabled(user)) {
//            jwtClaimsSet.claim(JwtTokenConstants.IS_TWO_FACTOR_ENABLED.getName(), true);
//            jwtClaimsSet.claim(JwtTokenConstants.TWO_FACTOR_AUTHENTICATED.getName(), false);
//        }
        return jwtClaimsSet.build();
    }

    private JWTClaimsSet.Builder getCommonClaims(UserDetails userDetails) {

        Customer customer = (Customer) userDetails;
        JWTClaimsSet.Builder jwtClaimsSet = new JWTClaimsSet.Builder()
                .audience(JwtTokenConstants.CUSTOMER)
                .subject(JwtTokenConstants.AUTH)
                .issuer(JwtTokenConstants.COSMO)
                .claim(JwtTokenConstants.USER_ID, customer.getId())
                .claim(JwtTokenConstants.USERNAME, customer.getUsername());
//        if (isTemp(user)) {
//            jwtClaimsSet.claim(JwtTokenConstants.IS_TEMP.getName(), true);
//            jwtClaimsSet.claim(JwtTokenConstants.ROLES.getName(), getRoles(userAuth));
//        } else {
//            jwtClaimsSet.claim(JwtTokenConstants.ROLES.getName(), getRoles(userAuth));
//        }
        return jwtClaimsSet;
    }

    //
    private boolean isTemp(UserDetails user) {
        return false;
    }

}
