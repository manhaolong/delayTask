package com.ismarthealth.delaytask.server.controller;

import com.ismarthealth.delaytask.server.common.constant.Constants;
import com.ismarthealth.delaytask.server.factory.ClassUtil;
import com.ismarthealth.delaytask.server.pojo.vo.ExpireRedisRequestVo;
import com.ismarthealth.delaytask.server.redis.RedisService;
import com.ismarthealth.delaytask.server.template.AbstractExpiredCallBackTemplate;
import com.ismarthealth.osp.core.common.Result;
import com.ismarthealth.osp.core.common.enums.ReturnCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * OrderController
 *
 * @author Liuxk
 */
@RestController
@Api(value = "ExpiredRedisCallController", tags = "redis回调controller")
@RequestMapping("/redis")
public class ExpiredRedisCallController {

    private static final Logger logger = LoggerFactory.getLogger(ExpiredRedisCallController.class);

    @Autowired
    private RedisService redisService;

    /**
     * 调用延时任务controller入口
     * expireRedisRequestVo 类 包含
     * sourceCode : 区分不同的业务,如:order 代表订单调用,后期会根据order 找到对应的模板方法子类去执行对应的业务逻辑
     * id : 传递的业务逻辑表对应的主键id
     * expireTime : 非必传项,如果业务有单独设置在redis 对应key的失效时间,就会覆盖模板类中默认设置的失效时间
     *
     * @param expireRedisRequestVo
     * @return
     * @author liuxk
     */
    @RequestMapping(value = "/expiredRedisCall", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result expiredRedisCall(@RequestBody ExpireRedisRequestVo expireRedisRequestVo) {
        AbstractExpiredCallBackTemplate expiredCallBackTemplate = ClassUtil.getChildClass(expireRedisRequestVo.getSourceCode());
        expiredCallBackTemplate.handlerBusiness(expireRedisRequestVo.getId(), expireRedisRequestVo.getSourceCode(), expireRedisRequestVo.getExpireTime());
        return new Result(ReturnCodeEnum.Code1000000.getCode(), "redis key失效回调成功.");
    }

    /**
     * 删除接口 :
     * 旨在调用了延时任务后,不想在key达到失效时间内去执行对应的逻辑,阻断回调后逻辑执行
     *
     * @param sourceCode sourceCode : 区分不同的业务,如:order 代表订单调用,后期会根据order 找到对应的模板方法子类去执行对应的业务逻辑
     * @param id         id : 传递的业务逻辑表对应的主键id
     * @return
     */
    @RequestMapping(value = "/deleteRedisKey", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result deleteRedisKey(@RequestParam(required = true) @ApiParam("sourceCode:区分不同的业务逻辑") String sourceCode,
                                 @RequestParam(required = true) @ApiParam("id:不同的业务逻辑对应数据库主键id") String id) {
        // 组合redis key
        String redisKey = Constants.REDIS_KEY_PREFIX + sourceCode + Constants.REDIS_KEY_CONNECT + id;
        logger.info("需要处理的业务逻辑id为{},对应redis库key为{},对应业务code为{}", id, redisKey, sourceCode);
        redisService.del(redisKey);
        redisService.del(redisKey + ".lock");
        return new Result(ReturnCodeEnum.Code1000000.getCode(), "对应redis key删除成功!");
    }
}
