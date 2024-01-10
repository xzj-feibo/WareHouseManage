package com.xzj.controller;

import com.xzj.constant.Const;
import com.xzj.resp.PageResp;
import com.xzj.resp.Resp;
import com.xzj.service.IGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * author DunZhu
 * project IMS
 * date 2024/1/8
 **/
@RestController
@RequestMapping(Const.URL)
public class GoodsController {

    @Resource
    IGoodsService service;

    @PostMapping("Goods/list")
    public PageResp selectDeptList(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit,
                                   @RequestParam(value = "goodsName",required = false) String goodsName,
                                   @RequestParam(value = "goodsNo",required = false) String goodsNo){
        return service.selectGoodsList(page,limit,goodsName,goodsNo);
    }

    @PostMapping("Goods/save")
    public Resp saveOrUpdate(@RequestParam(value = "goodsId",required = false) Long goodsId,
                             @RequestParam(value = "goodsName")String goodsName,
                             @RequestParam("goodsNo") String goodsNo
    ){
        return service.saveOrUpdate(goodsId, goodsName,goodsNo);
    }

    @DeleteMapping("Goods/delete")
    public Resp delete(){
        return null;
    }
}
