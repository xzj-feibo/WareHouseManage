package com.xzj.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Preconditions;
import com.xzj.auth.AuthInfo;
import com.xzj.exception.ImsAuthException;

import java.util.*;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/7 9:48
 */
public class JwtUtil {
    //Base64负载，包括用户的相关信息
    public static String USER_ID = "userId";
    public static String USER_NAME = "userName";
    public static String USER_ROLES = "roles";

    /**
     * 使用jwt生成token，Signature = HMAC256(Base64(头部)+"."+Base64(负载),secretKey)
     * @param authInfo
     * @param expireDate
     * @param secret
     * @return
     */
    public static String getToken(AuthInfo authInfo,Date expireDate,String secret){
        Preconditions.checkArgument(authInfo != null,"加密内容不能为空");
        Preconditions.checkArgument(expireDate != null,"过期时间异常");
        Preconditions.checkArgument(secret != null,"加密密码不能为null");
        Map<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");

        String token;//签名
        token = JWT.create()
                .withClaim(USER_ID,authInfo.getUserId()) //Payload,key-value
                .withClaim(USER_NAME,authInfo.getUserName())
                .withClaim(USER_ROLES,authInfo.getRoleId())
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(secret)); //签名使用的密钥secret
        return token;
    }

    /**
     * 验证token正确性并返回AuthInfo对象
     * @param token
     * @param secret
     * @return
     */
    public static AuthInfo verifyToken(String token,String secret){
        /**
         * 通过接收前端的token，用base64 URL解码出原文
         * 从header中获取哈希签名的算法，从payload中获取有效的数据
         * 再使用密钥加密一次对比原来的token，一致则通过
         */
        //校验器
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt;
        try {
            //该函数会将token分为三部分，header | payload | signature,然后使用签名算法和密钥对前两部分再次签名，与signature对比
            //相同则验签成功，否则失败
            jwt = verifier.verify(token);
        }catch (Exception e){
            throw new ImsAuthException("凭证已过期，请重新登录");
        }
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserId(jwt.getClaim(USER_ID).asLong());
        authInfo.setUserName(jwt.getClaim(USER_NAME).asString());
        authInfo.setRoleId(jwt.getClaim(USER_ROLES).asLong());
        return authInfo;
    }
}
