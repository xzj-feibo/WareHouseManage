package com.xzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.auth.AuthInfo;
import com.xzj.mapper.DeptMapper;
import com.xzj.mapper.RoleMapper;
import com.xzj.mapper.UsersMapper;
import com.xzj.mdc.MDCKey;
import com.xzj.model.Users;
import com.xzj.resp.*;
import com.xzj.service.IUserService;
import com.xzj.utils.JwtUtil;
import com.xzj.utils.RedisUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/6 18:36
 */
@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper,Users> implements IUserService {
    @Resource
    private UsersMapper mapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private RoleMapper roleMapper;
    @Value("${jwt.auth.secret}")
    private String secret;
    @Value("${jwt.auth.expired}")
    private Integer expired;
    @Autowired(required = false)
    private RedisUtil redisUtil;
    @Override
    public LoginResp login(String account, String password) {
        LoginResp loginResp = new LoginResp();
        UserResp userResp = mapper.login(account, password);
        if (userResp == null){
            loginResp.setMessage("账号或密码不存在");
            loginResp.setSuccess(false);
            return loginResp;
        }

        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserId(userResp.getId());
        authInfo.setUserName(userResp.getUserName());
        authInfo.setRoleId(userResp.getRoleId());
        authInfo.setExpired(getExpiredTime());
        Date date = new Date(getExpiredTime());
        //根据authInfo生成token
        String token = JwtUtil.getToken(authInfo, date, secret);
        if (redisUtil != null){
            redisUtil.set(token,authInfo);
        }
        userResp.setToken(token);
        loginResp.setData(userResp);
        return loginResp;
    }

    public Long getExpiredTime(){
        return new Date().getTime() + expired;
    }

    @Override
    public PageResp<List<Users>> selectUserList(Integer page, Integer limit, String userName, String userMobile) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        PageHelper.startPage(page,limit);
        if(userName!=null&&!"".equals(userName)){
            queryWrapper.eq("user_name",userName);
        }
        if (userMobile!=null && !"".equals(userMobile)){
            queryWrapper.eq("phone",userMobile);
        }
        List<Users> list = mapper.selectList(queryWrapper);
        list.forEach(li -> {
            li.setRoleName(roleMapper.selectById(li.getRoleId()).getRoleName());
            li.setDeptName(deptMapper.selectById(li.getDeptId()).getDeptName());
            li.setCreateUserName(mapper.selectById(li.getCreateUser()).getUserName());
            li.setEditUserName(mapper.selectById(li.getUpdateUser()).getUserName());
        });
        PageInfo<Users> info = new PageInfo<>(list);
        PageResp<List<Users>> resp = new PageResp<>();
        resp.setCount(info.getTotal());
        resp.setData(list);
        return resp;
    }

    @Override
    public Resp saveOrUpdate(Long userId, String account, String userName, String userMobile, Long roleId, Long deptId) {
        Users users = new Users();
        users.setAccount(account);
        users.setUserName(userName);
        users.setUserMobile(userMobile);
        users.setDeptId(deptId);
        users.setRoleId(roleId);
        //新增
        if (userId == null){
//            users.setCreateTime(new Date());
            //初始密码
            users.setPassword("123456");
            users.setCreateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
            int insert = mapper.insert(users);
            Resp.toReturn(insert>0?"成功":"失败",insert>0);
        }
        //更新
        users.setId(userId);
//        users.setUpdateTime(new Date());
        users.setUpdateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
        int update = mapper.updateById(users);
        return Resp.toReturn(update>0?"成功":"失败",update>0);
    }

    @Override
    public Resp delete(Long userId) {
        int ret = mapper.deleteById(userId);
        return Resp.toReturn(ret>0?"成功":"失败",ret>0);
    }

    @Override
    public Resp resetPwd(Long userId) {
        int ret = mapper.resetPwd(userId);
        return Resp.toReturn(ret>0?"成功":"失败",ret>0);
    }
}
