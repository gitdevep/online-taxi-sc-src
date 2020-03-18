package com.online.taxi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

/**
 * jwt
 * @date 2018/08/15
 **/
public class JwtUtil {


    private static String secret = "ko346134h_we]rg3in_yip1!";

    /**
     * 生成TOKEN
     * @param subject subject
     * @param issueDate 时间
     * @return string
     */
    public static String createJWT(String subject, Date issueDate) {
        String compactJws = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issueDate)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();
        return compactJws;
    }

    /**
     * 解密 jwt
     * @param jwt 需要解密的字符串
     * @return Claims 对象
     */
    public static Claims parseJWT(String jwt)   {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
        return claims;
    }


}

