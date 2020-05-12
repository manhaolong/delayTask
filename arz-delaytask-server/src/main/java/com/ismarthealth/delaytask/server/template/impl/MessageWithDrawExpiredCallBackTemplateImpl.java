package com.ismarthealth.delaytask.server.template.impl;

import com.ismarthealth.delaytask.server.common.annotation.ExpiredCallBackConfig;
import com.ismarthealth.delaytask.server.feignclient.ImFeignClient;
import com.ismarthealth.delaytask.server.template.AbstractExpiredCallBackTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MHL
 * @version 1.0
 * @date 2020/3/11 21:52
 * @description im消息撤回模板子类
 */
@Service
public class MessageWithDrawExpiredCallBackTemplateImpl extends AbstractExpiredCallBackTemplate {

    @Autowired
    ImFeignClient imFeignClient;

    @Override
    public Boolean checkAlreadyCallBackIfNecessary(String primaryId) {
        //已经设置定时时间
        return false;
    }

    @Override
    public void doBusiness(String primaryId) {
        //redis 定时到期之后执行回调业务
        imFeignClient.imMsgRecallOrRemoveUpdateStatus(primaryId);
    }
}
