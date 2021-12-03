package com.yuki.experiment.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Slf4j
public class JwtUtil {

    private static String KEY;

    private static Integer TIME;

    @Value("${jwt.time}")
    public void setTIME(Integer time) {
        TIME = time;
    }

    @Value("${jwt.key}")
    public void setKey(String key) {
        KEY = key;
    }

    public static String getToken(JSONObject user) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, TIME);
        JWTCreator.Builder builder = JWT.create();

        Integer id = user.getInteger("id");
        String name = user.getString("name");
        String type = user.getString("type");

        builder.withClaim("id", id);
        builder.withClaim("name", name);
        builder.withClaim("type", type);
        return builder
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC384(KEY));
    }

    public static DecodedJWT checkToken(String token) {
        return JWT.require(Algorithm.HMAC384(KEY)).build().verify(token);
    }

    public static JSONObject getInfo(String token) {
        DecodedJWT jwt = checkToken(token);
        JSONObject json = new JSONObject();
        json.put("id", jwt.getClaim("id").asInt());
        json.put("name", jwt.getClaim("name").asString());
        json.put("type", jwt.getClaim("type").asString());
        return json;
    }

}
