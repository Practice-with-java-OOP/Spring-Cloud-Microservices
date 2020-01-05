package com.syphan.practice.auth.security.test;

import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;

/**
 * Created by TaiND on 2019-12-11.
 **/
@Log4j2
public class AdditionalTokenUtils {

    public static void verifyToken(String additionalToken, String secret) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(additionalToken).getBody();
            System.out.println();
        } catch (Exception ex) {
            log.error("The token with secret: " + secret + " is invalid: " + ex.getMessage());
        }
    }
}
