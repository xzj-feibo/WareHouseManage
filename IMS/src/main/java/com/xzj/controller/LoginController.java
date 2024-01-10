package com.xzj.controller;

import com.xzj.constant.Const;
import com.xzj.resp.LoginResp;
import com.xzj.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/6 15:26
 */
@RestController
@RequestMapping(Const.URL)
@Api("用户登录")
@Slf4j
public class LoginController {
    @Resource
    private IUserService service;
    @ApiOperation("登录")
    @PostMapping("login")
    public LoginResp login(@RequestParam("username") String account,
                           @RequestParam("password") String password
                           ){
        return service.login(account, password);
    }

}
