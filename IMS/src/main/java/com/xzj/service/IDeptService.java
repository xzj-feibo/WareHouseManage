package com.xzj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzj.model.Dept;
import com.xzj.resp.*;

import java.util.List;

public interface IDeptService extends IService<Dept> {
    /**
     * 查询所有部门
     * @param page 页面数
     * @param limit 每页数量
     * @param deptName 部门名
     * @param deptNo 部门号
     * @return
     */
    PageResp<List<Dept>> selectDeptList(Integer page, Integer limit, String deptName, String deptNo);

    Resp saveOrUpdate(Long deptId, String deptName, String deptNo);

    Resp delete(Long deptId);

    ListResp<List<Dept>> selectDeptListNoParams();
}
