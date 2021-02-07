package com.backstage.core.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * JWT token 工具类,提供JWT生成,校验,工作
 *
 * @author yangfeng
 * @Date 2019-09-17
 */
@ConfigurationProperties(prefix = "jwt")
@Component
public class JWTUtil {
    private static Logger LOG = LoggerFactory.getLogger(JWTUtil.class);
    //过期时间
    private static Long expireTime;

    // 秘钥
    private static String secret;


    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String fieldName,
                                 String fieldValue) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(fieldName, fieldValue).build();
            //效验TOKEN
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @param token
     * @param fieldName
     * @return
     */
    public static String getFieldValue(String token, String fieldName) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(fieldName).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 生成签名,5min后过期
     *
     * @param fields 字段名/值MAP
     * @return 加密的token
     */
    public static String sign(Map<String, Object> fields) {
        try {
            Date date = new Date(System.currentTimeMillis() + expireTime * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTCreator.Builder builder = JWT.create();
            for (Map.Entry entry : fields.entrySet()) {
                builder.withClaim(entry.getKey().toString(), entry.getValue().toString());
            }
            return builder.withExpiresAt(date).sign(algorithm);
        } catch (Exception e) {
            return null;
        }

    }

    public static Long getExpireTime() {
        return expireTime;
    }

    public static void setExpireTime(Long expireTime) {
        JWTUtil.expireTime = expireTime;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        JWTUtil.secret = secret;
    }
}