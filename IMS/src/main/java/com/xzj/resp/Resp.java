package com.xzj.resp;

import com.google.errorprone.annotations.Var;
import lombok.*;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/6 16:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resp {
    private boolean success = true;
    private String message;

    public static Resp toReturn(String msg,Boolean success){
        Resp resp = new Resp();
        resp.setSuccess(success);
        resp.setMessage(msg);
        return resp;
    }

}
