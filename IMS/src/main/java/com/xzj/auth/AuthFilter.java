package com.xzj.auth;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.xzj.local.UserThreadLocal;
import com.xzj.resp.Resp;
import com.xzj.utils.JsonUtil;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.xzj.constant.Const;
import com.xzj.exception.ImsAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/7 21:08
 */
@Slf4j
@NoArgsConstructor
public class AuthFilter implements Filter {
    private AuthenticatorImpl authenticator;
    private AuthenticatorRedisImpl authenticatorRedis;
    private Set<String> urifilter;

    public AuthFilter(AuthenticatorImpl authenticator,AuthenticatorRedisImpl authenticatorRedis) {
        this.authenticator = authenticator;
        this.authenticatorRedis = authenticatorRedis;
    }

    public AuthFilter(AuthenticatorImpl authenticatorimpl) {
        this.authenticator = authenticatorimpl;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        //得到需要放行参数，例如登录
        String initParameter = filterConfig.getInitParameter(Const.UN_FILTER_KEY);
        List<String> list;
        if (StringUtils.isEmpty(initParameter)){
            list = Collections.emptyList();
        }else{
            list = Lists.newArrayList(initParameter.split(","));
            list.removeIf(String::isEmpty);
        }
        urifilter= ImmutableSet.copyOf(list);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //得到请求的uri
        String uri = request.getRequestURI();
        //说明uri符合放行标准
        if (urifilter.stream().anyMatch(uri::startsWith)){
            //放行
                filterChain.doFilter(servletRequest,servletResponse);
                return;
        }
        //不符合放行标准，需要鉴权
        try {
            String header = request.getHeader("token"); //获取请求头中AUTHORIZATION值
            //AUTHORIZATION为空时直接获取名为token的字段值，否则获取AUTHORIZATION字段值
            String token = StringUtils.isEmpty(header) ? request.getParameter("token") : header;
            if (StringUtils.isEmpty(token)) {
                throw new ImsAuthException("未携带 token");
            }
            AuthInfo authInfo;
            //拿得到token就说明验签成功
            if (authenticatorRedis != null) {
                authInfo = authenticatorRedis.auth(token);
            }else{
                authInfo = authenticator.auth(token);
            }

            if (authInfo == null){
                throw  new RuntimeException("鉴权失败");
            }
            //保存当前用户信息
            UserThreadLocal.put(authInfo);
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (ImsAuthException e){
            //定义响应格式
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            //设置未鉴权状态码
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            String jsonString = JsonUtil.getJsonString(Resp.toReturn(e.getMessage(), false));
            response.getOutputStream().write(jsonString.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
