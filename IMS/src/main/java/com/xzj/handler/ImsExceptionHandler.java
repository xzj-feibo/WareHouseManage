package com.xzj.handler;

import com.xzj.exception.*;
import com.xzj.resp.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/17 9:47
 */
@Slf4j
@RestControllerAdvice           //用于定义全局的异常处理器（Global Exception Handler）类
public class ImsExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ImsAuthException.class)  //通过使用@ExceptionHandler注解并指定相应的异常类型，可以为不同类型的异常提供不同的处理逻辑，以便进行精细的异常处理和错误响应生成。
    public ResponseEntity<Resp> authExceptionHandler(ImsAuthException imsAuthException){
        printErrLog(imsAuthException);
        Resp resp = new Resp(false,imsAuthException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

    public void printErrLog(Exception e){
        log.error("异常:{}",e.getMessage());
    }

    @ExceptionHandler(ImsBadRequestException.class)
    public ResponseEntity<Resp> badRequestExceptionHandler(ImsBadRequestException imsBadRequestException){
        printErrLog(imsBadRequestException);
        Resp resp = new Resp(false,imsBadRequestException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }
    @ExceptionHandler(ImsNotFoundException.class)
    public ResponseEntity<Resp> notFoundExceptionHandler(ImsNotFoundException imsNotFoundException){
        printErrLog(imsNotFoundException);
        Resp resp = new Resp(false,imsNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

}
