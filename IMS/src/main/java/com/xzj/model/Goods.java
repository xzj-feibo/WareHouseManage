package com.xzj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * author DunZhu
 * project IMS
 * date 2024/1/8
 **/
@Data
@TableName("goods")
public class Goods extends Base{
    @TableId(type = IdType.AUTO)
    private Long id;
    private String goodsName;
    @TableField("goods_code")
    private String goodsNo;
    private Double price;
    private Integer inventory;
    private String stemPlace;
}
