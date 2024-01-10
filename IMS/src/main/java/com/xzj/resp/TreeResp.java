package com.xzj.resp;

import lombok.Data;

import java.util.List;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/14 10:01
 */
@Data
public class TreeResp<T> extends Resp{
    private T data;  //所有菜单
    private List list;  //用户拥有的菜单权限
}
