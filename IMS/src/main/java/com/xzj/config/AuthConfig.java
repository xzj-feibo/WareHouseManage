package com.xzj.config;

import com.google.common.base.Joiner;
import com.xzj.auth.*;
import com.xzj.constant.Const;
import com.xzj.utils.RedisUtil;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/7 21:11
 */

/**
 * 配置类，@Bean表示创建返回方法返回类型的bean
 */
@Configuration
public class AuthConfig {
    @Autowired(required = false)
    RedisTemplate redisTemplate;
    @Value("${jwt.auth.redis.enable}")
    private Boolean redisEnabled;
    @Autowired
    ApplicationContext context;
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter(){       //FilterRegistrationBean，Spring中用于注册和配置Filter的类
        FilterRegistrationBean<AuthFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        AuthFilter authFilter;
        if (redisEnabled){
            authFilter = new AuthFilter(authenticatorimpl(),authenticatorRedis());
        }else{
            authFilter = new AuthFilter(authenticatorimpl());
        }

        filterRegistrationBean.setFilter(authFilter);
        filterRegistrationBean.addUrlPatterns("/api/*");
        /**
         * 设置放行参数
         */
        filterRegistrationBean.addInitParameter(Const.UN_FILTER_KEY, Joiner.on(",").join(Const.URL+"login",Const.URL+"xxx"));
        filterRegistrationBean.setEnabled(true);
        return filterRegistrationBean;
    }

    @Bean
    public AuthenticatorImpl authenticatorimpl(){
        String secret = context.getEnvironment().getProperty("jwt.auth.secret");
        return new AuthenticatorImpl(secret);
    }

    @Bean
    @ConditionalOnProperty(value = "jwt.auth.redis.enable",havingValue = "true") //当enable值为真时才构建这个bean
    public AuthenticatorRedisImpl authenticatorRedis(){
        return new AuthenticatorRedisImpl(redisUtil());
    }

    @Bean
    @ConditionalOnProperty(value = "jwt.auth.redis.enable",havingValue = "true")
    public RedisUtil redisUtil(){
        return  new RedisUtil(redisTemplate);
    }
}
