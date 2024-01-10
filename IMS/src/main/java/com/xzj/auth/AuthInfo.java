package com.xzj.auth;

import lombok.Data;


/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/7 10:58
 */
@Data
public class AuthInfo {
    private Long userId;
    private String userName;
    private Long roleId;
    private Long expired;
    private String key;
}
