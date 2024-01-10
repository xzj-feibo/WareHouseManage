package com.xzj.exception;

import com.xzj.resp.Resp;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/17 9:29
 */
public class ImsNotFoundException extends ImsException{
    private Resp errorResp;

    public ImsNotFoundException(String message){
        super(message);
    }

    public ImsNotFoundException(Resp resp){
        super(resp.getMessage());
        this.errorResp = resp;
    }

    public ImsNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public ImsNotFoundException(Throwable cause){
        super(cause);
    }
}
