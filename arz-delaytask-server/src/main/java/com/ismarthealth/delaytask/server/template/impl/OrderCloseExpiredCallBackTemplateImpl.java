package com.ismarthealth.delaytask.server.template.impl;

import com.ismarthealth.delaytask.server.feignclient.DelayTaskOrderFeignClient;
import com.ismarthealth.delaytask.server.mapper.CustomerOrderModelMapper;
import com.ismarthealth.delaytask.server.template.AbstractExpiredCallBackTemplate;
import com.ismarthealth.osp.core.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 测试模板方法
 * @author: Liuxk
 * @create: 2020-01-04 14:10
 **/
@Service
public class OrderCloseExpiredCallBackTemplateImpl extends AbstractExpiredCallBackTemplate {

    @Autowired
    DelayTaskOrderFeignClient delayTaskOrderFeignClient;
    private static final Logger logger = LoggerFactory.getLogger(OrderCloseExpiredCallBackTemplateImpl.class);

    @Autowired
    private CustomerOrderModelMapper customerOrderModelMapper;

    @Override
    public Boolean checkAlreadyCallBackIfNecessary(String orderId) {
        Result result = delayTaskOrderFeignClient.checkOrderAlreadyCallBackIfNecessary(orderId);
        return (Boolean) result.getData();
    }

    @Override
    public void doBusiness(String orderId) {
        delayTaskOrderFeignClient.doOrderClose(orderId);
    }

}
