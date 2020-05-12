package com.ismarthealth.delaytask.server.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author: HMG
 * @Date 2018/7/17
 * @Description: 不需验证登录信息注解,给LoginInterceptor使用
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpiredCallBackConfig {
    public String sourceCode();
}
