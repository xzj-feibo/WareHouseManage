package com.xzj.controller;

import com.xzj.constant.Const;
import com.xzj.resp.PageResp;
import com.xzj.resp.Resp;
import com.xzj.service.IGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;

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
                             @RequestParam("goodsNo") String goodsNo,
                             @RequestParam("price") Double price,
                             @RequestParam("inventory") Integer inventory,
                             @RequestParam("stemPlace") String stemPlace
    ){
        return service.saveOrUpdate(goodsId, goodsName,goodsNo,price,inventory,stemPlace);
    }

    @DeleteMapping("Goods/delete")
    public Resp delete(@RequestParam(value = "ids") Long goodsId){
        return service.delete(goodsId);
    }
}
