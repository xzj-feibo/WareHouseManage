package com.xzj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Role extends Base{
    @TableId(type = IdType.AUTO)
    private Long id;

    private String roleName;

    @TableField(value = "role_code")
    private String roleNo;
}