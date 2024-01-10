package com.xzj.controller;

import com.xzj.constant.Const;
import com.xzj.resp.*;
import com.xzj.service.IDeptService;
import com.xzj.service.impl.DeptServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/12 15:13
 */
@RestController
@RequestMapping(Const.URL)
public class DeptController {
    @Resource
    IDeptService service;

    @PostMapping("Dept/list")
    public PageResp selectDeptList(@RequestParam("page") Integer page,
                               @RequestParam("limit") Integer limit,
                               @RequestParam(value = "deptName",required = false) String deptName,
                               @RequestParam(value = "deptNo",required = false) String deptNo){
        return service.selectDeptList(page,limit,deptName,deptNo);
    }

    @PostMapping("Dept/save")
    public Resp saveOrUpdate(@RequestParam(value = "deptId",required = false) Long deptId,
                             @RequestParam(value = "deptName")String deptName,
                             @RequestParam("deptNo") String deptNo
    ){
        return service.saveOrUpdate(deptId, deptName, deptNo);
    }

    @PostMapping("Dept/list/no/params")
    public ListResp selectDeptListNoParams(){
        return service.selectDeptListNoParams();
    }

    @DeleteMapping("Dept/delete")
    public Resp delete(@RequestParam(value = "ids") Long deptId){
        return service.delete(deptId);
    }
}
