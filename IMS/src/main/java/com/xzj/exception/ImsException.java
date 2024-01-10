package com.xzj.exception;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/17 9:29
 */
public class ImsException extends RuntimeException{
    public ImsException(){
        super();
    }

    public ImsException(String message){
        super(message);
    }

    public ImsException(String message, Throwable cause){
        super(message, cause);
    }

    public ImsException(Throwable cause){
        super(cause);
    }
}
