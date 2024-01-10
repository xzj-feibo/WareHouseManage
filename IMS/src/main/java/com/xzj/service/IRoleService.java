package com.xzj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzj.model.Role;
import com.xzj.resp.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IRoleService extends IService<Role> {
    /**
     * 查询所有角色
     * @param page 页面数
     * @param limit 每页数量
     * @param deptName 部门名
     * @param deptNo 部门号
     * @return
     */
    PageResp selectRoleList(Integer page, Integer limit, String deptName, String deptNo);

    Resp saveOrUpdate(Long deptId, String roleName, String roleNo);

    Resp delete(Long roleId);

    TreeResp roleRight(Long roleId);
    Resp roleRightSave(Long roleId, List<Long> menuIds);

    ListResp selectRoleListNoParams();

}
