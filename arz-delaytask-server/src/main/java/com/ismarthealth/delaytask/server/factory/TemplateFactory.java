package com.ismarthealth.delaytask.server.factory;


import com.ismarthealth.delaytask.server.config.SpringUtils;
import com.ismarthealth.delaytask.server.exception.BaseException;
import com.ismarthealth.delaytask.server.template.AbstractExpiredCallBackTemplate;
import com.ismarthealth.osp.core.common.enums.ReturnCodeEnum;

/**
 * @author Liuxk
 * @description 获取模板子类工厂
 * @create 2020年1月10日13:53:11
 */
public class TemplateFactory {
    public static AbstractExpiredCallBackTemplate getExpiredCallBackTemplate(Class beanClass) {
        Object bean = SpringUtils.getBean(beanClass);
        if (bean == null) {
            throw new BaseException(ReturnCodeEnum.CODE_4000000.getCode(), "找不到bean!beanClass:" + beanClass);
        }
        if (bean instanceof AbstractExpiredCallBackTemplate) {
            return (AbstractExpiredCallBackTemplate) bean;
        }
        // TODO 暂时未设置code码
        throw new BaseException(ReturnCodeEnum.CODE_4000000.getCode(), "找到类不是对应模板的子类!" + bean.getClass());
    }
}