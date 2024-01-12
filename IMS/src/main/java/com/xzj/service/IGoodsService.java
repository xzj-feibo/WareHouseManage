package com.xzj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzj.model.Goods;
import com.xzj.resp.PageResp;
import com.xzj.resp.Resp;

/**
 * author DunZhu
 * project IMS
 * date 2024/1/8
 **/
public interface IGoodsService extends IService<Goods> {
    PageResp selectGoodsList(Integer page, Integer limit, String goodsName, String goodsNo);

    Resp saveOrUpdate(Long goodsId, String goodsName, String goodsNo,Double price,Integer inventory,String stemPlace);

    Resp delete(Long goodsId);
}
