package com.xzj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("dept")
public class Dept extends Base{
    @TableId(type = IdType.AUTO)
    private Long id;
    private String deptName;
    @TableField(value = "dept_code")
    private String deptNo;
}