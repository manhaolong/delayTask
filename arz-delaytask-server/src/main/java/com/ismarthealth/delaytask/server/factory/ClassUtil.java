package com.ismarthealth.delaytask.server.factory;


import com.ismarthealth.delaytask.server.common.constant.ChildClassConstants;
import com.ismarthealth.delaytask.server.exception.BaseException;
import com.ismarthealth.delaytask.server.template.AbstractExpiredCallBackTemplate;
import com.ismarthealth.osp.core.common.enums.ReturnCodeEnum;

/**
 * @description: 获取子类工具类
 * @author: Liuxk
 * @create: 2020-01-04 15:34
 **/
public class ClassUtil {

    /**
     * @return com.ismarthealth.delaytask.server.template.AbstractExpiredCallBackTemplate
     * @description 通过传入的业务来源code获取到对应的子类
     * @params [sourceCode]
     * @author Liuxk
     * @creat 2020/1/9
     * @修改人和其它信息
     */
    public static AbstractExpiredCallBackTemplate getChildClass(String sourceCode) {
        Class beanClass = ChildClassConstants.childClassMap.get(sourceCode);
        if (beanClass == null) {
            throw new BaseException(ReturnCodeEnum.CODE_4000000.getCode(), "sourceCode的模板类没有找到!sourceCode:" + sourceCode);
        }
        AbstractExpiredCallBackTemplate expiredCallBackTemplate = TemplateFactory.getExpiredCallBackTemplate(beanClass);
        return expiredCallBackTemplate;
    }
}
