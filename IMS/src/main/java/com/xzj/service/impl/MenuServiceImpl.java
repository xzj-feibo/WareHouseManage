package com.xzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzj.local.UserThreadLocal;
import com.xzj.mapper.MenuMapper;
import com.xzj.mapper.RoleMenuMapper;
import com.xzj.model.*;
import com.xzj.resp.ListResp;
import com.xzj.resp.Resp;
import com.xzj.service.IMenuService;

import com.xzj.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/13 11:44
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper,Menu> implements IMenuService {
    @Resource
    private MenuMapper mapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public ListResp<List<Menu>> selectMenuList() {
        //得到当前用户在系统中的角色id
        Long roleId = UserThreadLocal.get().getRoleId();
        List<Long> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查询出来的菜单列表
        List<Menu> menus = new ArrayList<>();
        menuIds.forEach(menuId ->{
            menus.add(mapper.selectById(menuId));
        });
        /**
         * 先找没有儿子的出来，再去找儿子
         */
        //父级菜单
        List<Menu> parentMenus = new ArrayList<>();
        //子级菜单
        List<Menu> sonMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getPid() == null){
                //没有pid，说明没有父级，已经是顶级菜单，添加到父级菜单中
                parentMenus.add(menu);
            }
            //添加到儿子菜单中
            sonMenus.add(menu);
        }
        //排好序的父级菜单
        //流式操作，加快集合处理速度
        List<Menu> parentOrderMenus = parentMenus.stream().sorted(Comparator.
                        comparing(Menu::getOrderValue)).
                collect(Collectors.toList());
        for (Menu orderMenu : parentOrderMenus) {
            //根据父级菜单的id和所有子菜单得到该父级菜单下的子菜单
            List<Menu> child = getChild(orderMenu.getId(),sonMenus);
            //排好序的子菜单
            List<Menu> collect = child.stream().
                    sorted(Comparator.comparing(Menu::getOrderValue))
                    .collect(Collectors.toList());
            orderMenu.setMenus(collect);
        }
        ListResp<List<Menu>> listResp = new ListResp<>();
        listResp.setData(parentOrderMenus);
        return listResp;
    }

    @Override
    public ListResp<List<MenuVo>> nodesList() {
        List<Menu> ret = mapper.selectList(new QueryWrapper<Menu>().isNull("pid"));
        ArrayList<MenuVo> list = new ArrayList<>();
        ret.forEach(r->{
            Long id = r.getId();
            String menuName = r.getMenuName();
            list.add(new MenuVo(id,menuName));
        });
        ListResp<List<MenuVo>> resp = new ListResp<>();
        resp.setData(list);
        return resp;
    }

    @Override
    public Resp saveOrUpdate(Long menuId, Long parentId, String menuIcon, String menuUrl, String menuName, Integer menuOrder) {
        Menu menu = new Menu();
        menu.setPid(parentId);
        menu.setIcon(menuIcon);
        menu.setUrl(menuUrl);
        menu.setMenuName(menuName);
        menu.setOrderValue(menuOrder);
        //新增
        if (menuId == null){
            int insert = mapper.insert(menu);
            Resp.toReturn(insert>0?"成功":"失败",insert>0);
        }
        //更新
        menu.setId(menuId);
        int update = mapper.updateById(menu);
        return Resp.toReturn(update>0?"成功":"失败",update>0);
    }

    @Override
    public Resp delete(List<Long> moduleIds) {
        int sum = 0;
        for (Long moduleId : moduleIds) {
            int ret = mapper.deleteById(moduleId);
            sum+=ret;
        }
        return Resp.toReturn(sum == moduleIds.size()?"成功":"失败",sum == moduleIds.size());
    }

    /**
     * 找属于该菜单id的 儿子
     * @param menuId
     * @param allMenus
     * @return
     */
    public List<Menu> getChild(Long menuId, List<Menu> allMenus){
        //找最近的儿子数据出来
        List<Menu> childMenus = new ArrayList<>();
        for (Menu allMenu : allMenus){
            if (allMenu.getPid() == menuId){
                childMenus.add(allMenu);
            }
        }
        if (childMenus.size() == 0){
            return childMenus;
        }
        //还要找儿子的儿子
        for (Menu childMenu : childMenus) {
            //说明还不是最低层的儿子
            if (StringUtils.isEmpty(childMenu.getUrl())){
                //递归
                childMenu.setMenus(getChild(childMenu.getId(),allMenus));
            }
        }
        return childMenus;
    }
}
