package com.xzj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_menu")
public class RoleMenu {
    @TableId(type = IdType.AUTO)
   private Long id;
   private Long menuId;
   private Long roleId;
}