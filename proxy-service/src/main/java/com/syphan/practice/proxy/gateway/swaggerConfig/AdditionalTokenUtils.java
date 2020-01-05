package com.syphan.practice.proxy.gateway.swaggerConfig;

import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;

/**
 * Created by TaiND on 2019-12-11.
 **/
public class AdditionalTokenUtils {

    public static void verifyToken(String additionalToken, String secret) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(additionalToken).getBody();
            System.out.println();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
