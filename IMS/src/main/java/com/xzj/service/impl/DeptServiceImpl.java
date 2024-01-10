package com.xzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.mapper.DeptMapper;
import com.xzj.mapper.UsersMapper;
import com.xzj.mdc.MDCKey;
import com.xzj.model.Dept;
import com.xzj.resp.*;
import com.xzj.service.IDeptService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/12 15:26
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper,Dept> implements IDeptService {
    @Resource
    private DeptMapper mapper;

    @Autowired
    private UsersMapper usersMapper;
    @Override
    public PageResp<List<Dept>> selectDeptList(Integer page, Integer limit, String deptName, String deptNo) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(deptName)) {
            queryWrapper.like("dept_name", deptName);
        }
        if (StringUtils.isNotBlank(deptNo)) {
            queryWrapper.eq("dept_code", deptNo);
        }
        List<Dept> list = mapper.selectList(queryWrapper);
        list.forEach(li->{
            li.setCreateUserName(usersMapper.selectById(li.getCreateUser()).getUserName());
            li.setEditUserName(usersMapper.selectById(li.getUpdateUser()).getUserName());
        });
        PageHelper.startPage(page,limit);

        PageInfo<Dept> info = new PageInfo<>(list);
        PageResp<List<Dept>> resp = new PageResp<>();
        resp.setCount(info.getTotal());
        resp.setData(list);
        return resp;
    }

    @Override
    public Resp saveOrUpdate(Long deptId, String deptName, String deptNo) {
        Dept dept = new Dept();
        dept.setDeptNo(deptNo);
        dept.setDeptName(deptName);
        //新增
        if (deptId == null){
            //初始密码
            dept.setCreateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
            int insert = mapper.insert(dept);
            Resp.toReturn(insert>0?"成功":"失败",insert>0);
        }
        //更新
        dept.setId(deptId);
        dept.setUpdateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
        int update = mapper.updateById(dept);
        return Resp.toReturn(update>0?"成功":"失败",update>0);
    }

    @Override
    public Resp delete(Long deptId) {
        int ret = mapper.deleteById(deptId);
        return Resp.toReturn(ret>0?"成功":"失败",ret>0);
    }

    @Override
    public ListResp<List<Dept>> selectDeptListNoParams() {
        List<Dept> ret = mapper.selectList(new QueryWrapper<>());
        ListResp<List<Dept>> resp = new ListResp<>();
        resp.setData(ret);
        return resp;
    }
}
