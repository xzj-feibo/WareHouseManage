package com.xzj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String icon;
    private String menuName;
    @TableField(value = "has_Third")
    private String hasThird;
    private String url;
    private Long pid;
    private Integer orderValue;
    @TableField(exist = false)
    private List<Menu> menus;
}