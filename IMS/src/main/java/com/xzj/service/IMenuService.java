package com.xzj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzj.model.Menu;
import com.xzj.resp.ListResp;
import com.xzj.resp.Resp;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    ListResp selectMenuList();

    ListResp nodesList();

    Resp saveOrUpdate(Long moduleId, Long parentId, String moduleIcon, String moduleUrl, String moduleName, Integer moduleOrder);

    Resp delete(List<Long> moduleIds);
}
