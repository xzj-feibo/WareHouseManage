package com.xzj.controller;

import com.xzj.constant.Const;
import com.xzj.resp.*;
import com.xzj.service.IDeptService;
import com.xzj.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/12 15:13
 */
@RestController
@RequestMapping(Const.URL)
public class RoleController {
    @Resource
    IRoleService service;

    @PostMapping("Role/list")
    public PageResp selectRoleList(@RequestParam("page") Integer page,
                               @RequestParam("limit") Integer limit,
                               @RequestParam(value = "roleName",required = false) String roleName,
                               @RequestParam(value = "roleNo",required = false) String roleNo){
        return service.selectRoleList(page,limit,roleName,roleNo);
    }

    @PostMapping("Role/list/no/parms")
    public ListResp selectRoleListNoParams(){
        return service.selectRoleListNoParams();
    }

    @PostMapping("Role/save")
    public Resp saveOrUpdate(@RequestParam(value = "roleId",required = false) Long roleId,
                             @RequestParam(value = "roleName")String roleName,
                             @RequestParam("roleNo") String roleNo
    ){
        return service.saveOrUpdate(roleId, roleName, roleNo);
    }

    @DeleteMapping("Role/delete")
    public Resp delete(@RequestParam(value = "ids") Long roleId){
        return service.delete(roleId);
    }

    @GetMapping("RoleRight/tree/{id}")
    public TreeResp roleRight(@PathVariable(value = "id")Long id){
        return service.roleRight(id);
    }


    @PostMapping("RoleRight/save")
    public Resp roleRightSave(@RequestParam("roleId") Long roleId,
                              @RequestParam("moduleIds")List<Long>moduleIds){
        return service.roleRightSave(roleId,moduleIds);
    }
}
