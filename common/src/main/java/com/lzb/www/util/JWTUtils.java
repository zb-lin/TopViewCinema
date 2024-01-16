package com.lzb.www.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lzb.www.exception.WebException;
import redis.clients.jedis.Jedis;
import rpc.util.JedisUtil;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {
    private static final String SIGNATURE = "!@#$g^xbGG^&&5455e6sync1s3@##%&*^^";

    private JWTUtils() {
    }

    /**
     * 生成token
     */
    public static String getToken(Map<String, Integer> claimParam) {
        Calendar instance = Calendar.getInstance();
        // 默认1小时过期
        instance.add(Calendar.HOUR, 1);
        JWTCreator.Builder builder = JWT.create();
        claimParam.forEach(builder::withClaim);
        builder.withExpiresAt(instance.getTime());
        Jedis jedis = null;
        try {
            Integer id = claimParam.get("id");
            jedis = JedisUtil.getJedis();
            // 一天
            jedis.setex("login:" + id, 60 * 60 * 12, String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.closeJedis(jedis);
        }

        return builder.sign(Algorithm.HMAC256(SIGNATURE));
    }


    /**
     * 验证token合法性
     */
    public static boolean verify(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
            return true;
        } catch (Exception e) {
            throw new WebException("token 违法或过期", e);
        }
    }

    /**
     * 获取token信息方法
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT verify = null;
        if (verify(token)) {
            verify = JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
        }
        return verify;
    }


}
