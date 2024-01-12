package com.xzj.local;

import com.xzj.auth.AuthInfo;
import com.xzj.model.Users;

/**
 * author DunZhu
 * project IMS
 * date 2024/1/12
 **/
public class UserThreadLocal {
    private static final ThreadLocal<AuthInfo> threadLocal=new ThreadLocal<>();
    public static void put(AuthInfo authInfo){
        threadLocal.set(authInfo);
    }
    public static AuthInfo get(){
        return threadLocal.get();
    }
    public static void remove(){
        threadLocal.remove();
    }
}
