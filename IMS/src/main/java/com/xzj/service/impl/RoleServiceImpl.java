package com.xzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.local.UserThreadLocal;
import com.xzj.mapper.MenuMapper;
import com.xzj.mapper.RoleMapper;
import com.xzj.mapper.RoleMenuMapper;
import com.xzj.mapper.UsersMapper;
import com.xzj.model.*;
import com.xzj.resp.*;
import com.xzj.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/12 18:33
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements IRoleService {
    @Resource
    private RoleMapper mapper;

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Override
    public PageResp<List<Role>> selectRoleList(Integer page, Integer limit, String roleName, String roleNo) {
        PageHelper.startPage(page,limit);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(roleName!=null && !"".equals(roleName)){
            queryWrapper.eq("role_name",roleName);
        }
        if(roleNo!=null&&!"".equals(roleNo)){
            queryWrapper.eq("role_code",roleNo);
        }
        List<Role> list = mapper.selectList(queryWrapper);
        list.forEach(li->{
            li.setCreateUserName(usersMapper.selectById(li.getCreateUser()).getUserName());
            if(li.getUpdateUser()!=null){
                li.setEditUserName(usersMapper.selectById(li.getUpdateUser()).getUserName());
            }
        });
        PageInfo<Role> info = new PageInfo<>(list);

        PageResp<List<Role>> resp = new PageResp<>();
        resp.setCount(info.getTotal());
        resp.setData(list);
        return resp;
    }

    @Override
    public Resp saveOrUpdate(Long roleId, String roleName, String roleNo) {
        Role role = new Role();
        role.setRoleNo(roleNo);
        role.setRoleName(roleName);
        //新增
        if (roleId == null){
            //初始密码
            role.setCreateUser(UserThreadLocal.get().getUserId());
            int insert = mapper.insert(role);
            Resp.toReturn(insert>0?"成功":"失败",insert>0);
        }
        //更新
        role.setId(roleId);
        role.setUpdateUser(UserThreadLocal.get().getRoleId());
        int update = mapper.updateById(role);
        return Resp.toReturn(update>0?"成功":"失败",update>0);
    }
//
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp delete(Long roleId) {
        //1.删除角色
        int ret = mapper.deleteById(roleId);
        //2.删除角色所拥有的所有权限
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);
        return Resp.toReturn(ret>0?"成功":"失败",ret>0);
    }

    @Override
    public TreeResp<List<Menu>> roleRight(Long roleId) {
        List<Menu> menus = menuMapper.selectList(null);
        TreeResp<List<Menu>> ret = new TreeResp<>();
        ret.setData(menus);

        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        ret.setList(roleMenus);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp roleRightSave(Long roleId, List<Long> menuIds) {
        //删除之前的记录
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
         //批量插入数据
        int ret = 0;
        for (Long menuId : menuIds) {
            ret+=roleMenuMapper.insert(new RoleMenu(null,menuId,roleId));
        }
        return Resp.toReturn(ret == menuIds.size()?"成功":"失败",ret == menuIds.size());
    }

    @Override
    public ListResp<List<Role>> selectRoleListNoParams() {
        List<Role> ret = mapper.selectList(null);
        ListResp<List<Role>> rsp = new ListResp<>();
        rsp.setData(ret);
        return rsp;
    }
}
