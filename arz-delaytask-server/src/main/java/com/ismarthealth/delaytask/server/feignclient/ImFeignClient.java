package com.ismarthealth.delaytask.server.feignclient;

import com.ismarthealth.osp.core.common.Result;
import com.ismarthealth.osp.core.common.pojo.to.ImRoomInfoExtModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @Author: MHL
 * @Date 2020/02/12
 * @Description: Im
 */
@FeignClient("arz-dsp-server")
public interface ImFeignClient {

    /**
     * 更新im消息/im消息撤回记录表状态
     * @param primaryId
     * @return
     */
    @RequestMapping(value = "/im/imMsgRecallOrRemoveUpdateStatus", method = RequestMethod.GET)
    Result imMsgRecallOrRemoveUpdateStatus(@RequestParam("primaryId") String primaryId);
}
