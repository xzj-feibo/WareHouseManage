package com.xzj.controller;

import com.xzj.constant.Const;
import com.xzj.model.Users;
import com.xzj.resp.*;
import com.xzj.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/8 16:56
 */
@RestController
@RequestMapping(Const.URL)
public class  UserController {
    @Resource
    IUserService service;

    @PostMapping("User/list")
    public PageResp<List<Users>> selectUserList(@RequestParam("page") Integer page,
                                                @RequestParam("limit") Integer limit,
                                                @RequestParam(value = "userName",required = false)String userName,
                                                @RequestParam(value = "userMobile",required = false)String userMobile
    ){
        return service.selectUserList(page, limit, userName, userMobile);
    }

    @PostMapping("User/save")
    public Resp saveOrUpdate(@RequestParam(value = "userId",required = false) Long userId,
                             @RequestParam(value = "userName")String userName,
                             @RequestParam(value = "gender") String gender,
                             @RequestParam("account") String account,
                             @RequestParam(value = "userMobile")String userMobile,
                             @RequestParam(value = "roleId") Long roleId,
                             @RequestParam(value = "deptId") Long deptId
    ){
        return service.saveOrUpdate(userId, account, userName, gender,userMobile, roleId, deptId);
    }

    @DeleteMapping("User/delete")
    public Resp delete(@RequestParam(value = "ids") Long userId){
        return service.delete(userId);
    }

    @PostMapping("User/pwd")
    public Resp reset(@RequestParam("userId") Long userId){
        return service.resetPwd(userId);
    }

}
