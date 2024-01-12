package com.xzj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user1")
public class Users extends Base{

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private String userName;

    private String account;

    private String gender;

    private String password;

    private Long deptId;

    @TableField(value = "phone")
    private String userMobile;

    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private String deptName;
}