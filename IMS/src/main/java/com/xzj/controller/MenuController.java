package com.xzj.controller;

import com.xzj.constant.Const;
import com.xzj.resp.ListResp;
import com.xzj.resp.Resp;
import com.xzj.service.IMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/13 10:54
 */
@RestController
@RequestMapping(Const.URL)
public class MenuController {
    @Resource
    IMenuService service;

    @GetMapping("menu")
    public ListResp selectMenuList(){
        return service.selectMenuList();
    }

    @PostMapping("Module/list")
    public ListResp menuList(){
        return service.selectMenuList();
    }

    @PostMapping("Module/nodes")
    public ListResp nodesList(){
        return service.nodesList();
    }

    @PostMapping("Module/save")
    public Resp saveOrUpdate(@RequestParam(value = "moduleId",required = false) Long menuId,
                             @RequestParam(value = "parentId",required = false) Long parentId,
                             @RequestParam(value = "moduleIcon")String menuIcon,
                             @RequestParam(value = "moduleUrl")String menuUrl,
                             @RequestParam(value = "moduleName")String menuName,
                             @RequestParam("moduleOrder") Integer menuOrder
    ){
        return service.saveOrUpdate(menuId, parentId, menuIcon, menuUrl, menuName, menuOrder);
    }

    @DeleteMapping("Module/delete")
    public Resp delete(@RequestParam(value = "ids") List<Long> moduleIds){
        return service.delete(moduleIds);
    }
}
