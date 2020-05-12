package com.ismarthealth.delaytask.server.feignclient;

import com.ismarthealth.osp.core.common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuxk
 * @createtime 2020年2月6日15:58:00
 * @description 远程调用延时任务
 */
@FeignClient("arz-h5-server")
public interface DelayTaskOrderFeignClient {

    @RequestMapping(value = "/customerOrder/checkOrderAlreadyCallBackIfNecessary", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    Result checkOrderAlreadyCallBackIfNecessary(@RequestParam("orderId") String orderId);

    @RequestMapping(value = "/customerOrder/doOrderClose", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    Result doOrderClose(@RequestParam("orderId") String orderId);
}
