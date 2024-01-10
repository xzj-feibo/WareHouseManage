package com.xzj.auth;

public interface Authenticator {
    /**
     * 认证器做认证使用
     * @param token
     * @return
     */
    AuthInfo auth(String token);
}
