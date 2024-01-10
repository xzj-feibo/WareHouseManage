package com.xzj.exception;

import com.xzj.resp.Resp;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/17 9:29
 */
public class ImsBadRequestException extends ImsException{
    private Resp errorResp;

    public ImsBadRequestException(String message){
        super(message);
    }

    public ImsBadRequestException(Resp resp){
        super(resp.getMessage());
        this.errorResp = resp;
    }

    public ImsBadRequestException(String message, Throwable cause){
        super(message, cause);
    }

    public ImsBadRequestException(Throwable cause){
        super(cause);
    }
}
