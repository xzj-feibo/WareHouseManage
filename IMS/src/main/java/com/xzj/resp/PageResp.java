package com.xzj.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/8 17:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResp<T> extends Resp{
    private T data;
    private Long count;
}
