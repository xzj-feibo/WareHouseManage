package com.xzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
            li.setEditUserName(usersMapper.selectById(li.getUpdateUser()).getUserName());
        });

        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);

        return new PageResp<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Resp saveOrUpdate(Long goodsId, String goodsName, String goodsNo) {
    return null;
    }
}
