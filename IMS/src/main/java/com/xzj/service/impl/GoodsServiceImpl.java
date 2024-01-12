package com.xzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.local.UserThreadLocal;
import com.xzj.mapper.GoodsMapper;
import com.xzj.mapper.UsersMapper;
import com.xzj.model.Goods;
import com.xzj.resp.PageResp;
import com.xzj.resp.Resp;
import com.xzj.service.IGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * author DunZhu
 * project IMS
 * date 2024/1/8
 **/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Resource
    private GoodsMapper mapper;
    @Resource
    private UsersMapper usersMapper;
    @Override
    public PageResp<List<Goods>> selectGoodsList(Integer page, Integer limit, String goodsName, String goodsNo) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(goodsName)) {
            queryWrapper.like("goods_name", goodsName);
        }
        if (StringUtils.isNotBlank(goodsNo)) {
            queryWrapper.eq("goods_code", goodsNo);
        }
        PageHelper.startPage(page,limit);
        List<Goods> goodsList = mapper.selectList(queryWrapper);

        goodsList.forEach(li->{
            li.setCreateUserName(usersMapper.selectById(li.getCreateUser()).getUserName());
            if(li.getUpdateUser()!=null){
                li.setEditUserName(usersMapper.selectById(li.getUpdateUser()).getUserName());
            }
        });

        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);

        return new PageResp<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Resp saveOrUpdate(Long goodsId, String goodsName, String goodsNo,Double price,Integer inventory,String stemPlace) {
        Goods goods = new Goods();
        goods.setGoodsNo(goodsNo);
        goods.setGoodsName(goodsName);
        if (price!=null){
            goods.setPrice(price);
        }
        if (inventory!=null){
            goods.setInventory(inventory);
        }
        if (stemPlace!=null){
            goods.setStemPlace(stemPlace);
        }
        //新增
        if (goodsId == null){
            goods.setCreateUser(UserThreadLocal.get().getUserId());
            int insert = mapper.insert(goods);
            Resp.toReturn(insert>0?"成功":"失败",insert>0);
        }
        //更新,goodsId肯定不为空
        goods.setId(goodsId);
        goods.setUpdateUser(UserThreadLocal.get().getUserId());
        int update = mapper.updateById(goods);
        return Resp.toReturn(update>0?"成功":"失败",update>0);
    }

    @Override
    public Resp delete(Long goodsId) {
        int ret = mapper.deleteById(goodsId);
        return Resp.toReturn(ret>0?"成功":"失败",ret>0);
    }
}
