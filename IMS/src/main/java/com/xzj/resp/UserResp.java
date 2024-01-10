package com.xzj.resp;

import lombok.Data;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/6 16:15
 */
@Data
public class UserResp {
    private Long id;

    private String userName;

    private Long deptId;

    private Long roleId;

    private String phone;

    private String token;
}
