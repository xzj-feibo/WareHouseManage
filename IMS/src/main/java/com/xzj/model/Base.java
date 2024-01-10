package com.xzj.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

/**
 * author DunZhu
 * project IMS
 * date 2024/1/9
 **/
@Data
public class Base {
    private Long createUser;

    private Long updateUser;

    private Timestamp createTime;

    @TableField("update_time")
    private Timestamp editTime;

    @TableField(exist = false)
    private String createUserName;

    @TableField(exist = false)
    private String editUserName;
}
