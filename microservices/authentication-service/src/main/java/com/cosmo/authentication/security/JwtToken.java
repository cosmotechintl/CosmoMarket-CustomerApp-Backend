package com.cosmo.authentication.security;

import com.cosmo.authentication.core.constant.JwtTokenConstants;
import com.cosmo.authentication.core.service.JwtService;
import com.cosmo.common.exception.ConflictException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

@Component
@Slf4j
public class JwtToken implements Serializable {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Value("${SECRET_KEY}")
    private String JWT_SECRET_KEY;

    @Autowired
    private JwtService  jwtService;

    public String getGroupTypeName() {
        String token = getToken();
        return (String) jwtService.extractAllClaims(token).get(JwtTokenConstants.GROUP);
    }

    public String getToken() {
        String header = httpServletRequest.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return header;
    }

    public JWTClaimsSet getAllClaimsFromToken(String token) {
        if (token != null && !token.isEmpty() && !token.equalsIgnoreCase("null")) {
            // Extract the token from the "Bearer " prefix if present
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            try {
                SignedJWT signedJWT = decryptToken(token);
                return signedJWT.getJWTClaimsSet();
            } catch (ParseException ex) {
                log.error("Exception: " + ex.getMessage());
                throw new ConflictException("Token is not valid");
            }
        } else {
            log.error("No token found in request headers.");
            throw new ConflictException("No token found in request headers");
        }
    }


    public SignedJWT decryptToken(String token) {
        try {
            JWEObject jweObject = JWEObject.parse(token);
            DirectDecrypter directDecrypter = new DirectDecrypter(JWT_SECRET_KEY.getBytes("UTF-8"));
            jweObject.decrypt(directDecrypter);
            if (verifySignature(jweObject.getPayload().toSignedJWT())) {
                return jweObject.getPayload().toSignedJWT();
            }
            log.error("Invalid Signature");
            return null;
        } catch (ParseException | UnsupportedEncodingException | JOSEException ex) {
            log.error("Exception : " + ex.getMessage());
            return null;
        }
    }

    public boolean verifySignature(SignedJWT signedJWT) throws JOSEException, ParseException {
        RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
        return signedJWT.verify(new RSASSAVerifier(publicKey));
    }



}


