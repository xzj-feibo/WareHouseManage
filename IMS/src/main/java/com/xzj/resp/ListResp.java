package com.xzj.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author DunZhu
 * @version 1.0
 * @date 2023/7/8 17:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListResp<T> extends Resp{
    private T data;
}
