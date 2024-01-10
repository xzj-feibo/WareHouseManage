package com.xzj.resp;

import lombok.Data;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/6 17:21
 */
@Data
public class LoginResp extends Resp{
    private UserResp data;
}
