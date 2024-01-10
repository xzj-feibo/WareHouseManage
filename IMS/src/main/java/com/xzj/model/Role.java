package com.xzj.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class Role extends Base{
    private Long id;

    private String roleName;

    @TableField(value = "role_code")
    private String roleNo;
}