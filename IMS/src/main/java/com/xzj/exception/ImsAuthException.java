package com.xzj.exception;

import com.xzj.resp.Resp;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/17 9:29
 */
public class ImsAuthException extends ImsException{
    private Resp errorResp;

    public ImsAuthException(String message){
        super(message);
    }

    public ImsAuthException(Resp resp){
        super(resp.getMessage());
        this.errorResp = resp;
    }

    public ImsAuthException(String message, Throwable cause){
        super(message, cause);
    }

    public ImsAuthException(Throwable cause){
        super(cause);
    }
}
