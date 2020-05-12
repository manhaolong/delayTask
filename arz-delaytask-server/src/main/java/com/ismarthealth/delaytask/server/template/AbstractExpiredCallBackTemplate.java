package com.ismarthealth.delaytask.server.template;

import com.ismarthealth.delaytask.server.common.annotation.ExpiredCallBackConfig;
import com.ismarthealth.delaytask.server.common.constant.ChildClassConstants;
import com.ismarthealth.delaytask.server.common.constant.Constants;
import com.ismarthealth.delaytask.server.common.eum.DelayTaskStatusEnum;
import com.ismarthealth.delaytask.server.exception.BaseException;
import com.ismarthealth.delaytask.server.mapper.DelayTaskLogModelMapper;
import com.ismarthealth.delaytask.server.pojo.DelayTaskLogModel;
import com.ismarthealth.delaytask.server.redis.RedisService;
import com.ismarthealth.osp.core.common.enums.ReturnCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * @description: redis key失效回调处理公共抽象类
 * @author: Liuxk
 * @create: 2020-01-02 15:08
 **/
@Component
public abstract class AbstractExpiredCallBackTemplate {

    @Autowired
    private RedisService redisService;

    /**
     * @Author: HMG
     * @Date: 2020/1/15
     * @Description: 超时时间 正式环境 expireTime = 24 * 60 * 60;
     *                          测试环境暂时 expireTime = 1 * 60 * 60;
     */
    public long expireTime = 24 * 60 * 60 ;
    /**
     * @Author: HMG
     * @Date: 2020/1/15
     * @Description: 随机超时时间最大秒数 max 2分钟  120秒
     */
    public int timeRandomSeed = 20;

    /**
     * @Description: 随机超时时间最大秒数 max 2分钟  120秒
     */
    public int maxTimeRandomSeed = 120;
    private static final Logger log = LoggerFactory.getLogger(AbstractExpiredCallBackTemplate.class);

    @Autowired
    private DelayTaskLogModelMapper delayTaskLogModelMapper;

    @PostConstruct
    public void init() {
        String sourceCode = getSourceCode();
        Class oldClass = ChildClassConstants.childClassMap.put(sourceCode, getClass());
        if (oldClass != null) {
            throw new RuntimeException("SourceCode已经被占用!sourceCode:" + sourceCode);
        }
    }


    public String getSourceCode() {
        if(getClass().isAnnotationPresent(ExpiredCallBackConfig.class)){
            ExpiredCallBackConfig expiredCallBackConfigAnnotation = getClass().getAnnotation(ExpiredCallBackConfig.class);
            String sourceCode = expiredCallBackConfigAnnotation.sourceCode();
            if(StringUtils.isBlank(sourceCode)){
                throw new RuntimeException(getClass().getName() + " SourceCode注解值不能为空");
            }
            return sourceCode;
        }
        String className = getClass().getSimpleName();
        String sourceCode = className.replace("ExpiredCallBackTemplateImpl", "");
        return toLowerCaseFirstOne(sourceCode);
    }

    /**
     * @Author: HMG
     * @Date: 2020/2/6
     * @Description: 首字母小写
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;

        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }


    /**
     * 1:模板第一步:生成key并对应设置失效时间
     * key的生成规则:EXPIREDCALL:(前缀)+sourceCode+":"+id
     * keyExpiredTime 失效时间对应单位为秒
     *
     * @param primaryId
     * @return
     */
    public String handlerBusiness(String primaryId, String sourceCode, Long businessSupplyExpireTime) {
        // 记录日志
        noteLogs(primaryId, sourceCode);
        // 组合redis key
        String redisKey = Constants.REDIS_KEY_PREFIX + sourceCode + Constants.REDIS_KEY_CONNECT + primaryId;
        long keyExpiredTime = getRedisKeyRandomExpiredTime(businessSupplyExpireTime);
        log.info("对应失效时间为:{}秒,业务id:{},对应业务标识code:{},redis key :{}", keyExpiredTime, primaryId, sourceCode, redisKey);
        redisService.setStr(redisKey + ".lock", Constants.REDIS_KEY_LOCK);
        redisService.steStr(redisKey, "value", keyExpiredTime);
        return redisKey;
    }

    /**
     * 2:幂等判断是否需要失效回调
     *
     * @param primaryId
     * @return
     */
    public abstract Boolean checkAlreadyCallBackIfNecessary(String primaryId);

    /**
     * 处理对应业务逻辑
     * 对应自己的业务逻辑处理,子类实现
     *
     * @param primaryId
     */
    public abstract void doBusiness(String primaryId);

    /**
     * 配置对应的失效时间
     */
    public final long getRedisKeyRandomExpiredTime(Long businessSupplyExpireTime) {
        int randomNum = getTimeRandomSeed();
        log.info("生成随机数为:{}秒,控制失效时间为:{},随机数最大值:{}", randomNum, expireTime, timeRandomSeed);
        return Long.parseLong((Objects.nonNull(businessSupplyExpireTime) ? businessSupplyExpireTime : expireTime) + randomNum + "");
    }

    /**
     * 记录日志
     *
     * @param primaryId
     */
    public void noteLogs(String primaryId, String sourceCode) {
        DelayTaskLogModel delayTaskLogModel = new DelayTaskLogModel();
        delayTaskLogModel.setBusinessId(primaryId);
        delayTaskLogModel.setBusinessType(sourceCode);
        delayTaskLogModel.setCreateTime(new Date());
        delayTaskLogModel.setStatus(DelayTaskStatusEnum.INIT.getIndex());
        delayTaskLogModelMapper.insertSelective(delayTaskLogModel);
    }

    /**
     * 模板采用随机Random去生成随机数,如果业务不想使用随机,可在子类扩展
     *
     * @return
     */
    public int getTimeRandomSeed() {
        // 限制不能超速
        if (timeRandomSeed > maxTimeRandomSeed) {
            throw new BaseException(ReturnCodeEnum.CODE_4000000.getCode(), "timeRandomSeed随机数最大值不能超过120!timeRandomSeed:" + timeRandomSeed);
        }
        // 随机N秒内生成一个数值避免过多key同时过期,服务崩溃
        Random random = new Random();
        int randomNum = random.nextInt(timeRandomSeed);
        return randomNum;
    }
}
