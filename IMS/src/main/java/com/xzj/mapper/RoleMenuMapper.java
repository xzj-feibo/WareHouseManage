package com.xzj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzj.model.Menu;
import com.xzj.model.RoleMenu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    @Select("select menu_id from role_menu where role_id = #{roleId}")
    List<Long> selectByRoleId(Long roleId);
}