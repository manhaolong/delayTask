package com.ismarthealth.delaytask.server.listener;

import com.ismarthealth.delaytask.server.common.constant.Constants;
import com.ismarthealth.delaytask.server.config.SpringUtils;
import com.ismarthealth.delaytask.server.factory.ClassUtil;
import com.ismarthealth.delaytask.server.redis.RedisService;
import com.ismarthealth.delaytask.server.template.AbstractExpiredCallBackTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * 监听redis过期
 *
 * @author liuxk
 */
@Component
public class RedisExpiredListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);

    /**
     * 监听定时短信
     *
     * @param message
     * @param bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        String key = new String(body);
        /**
         * 如果不是规定好的前缀,直接返回;
         */
        if (!key.startsWith(Constants.REDIS_KEY_PREFIX)) {
            return;
        }
        RedisService redisService = SpringUtils.getBean(RedisService.class);
        /**
         * 线上服务都是集群需要加锁,
         */
        try {
            String oldLock = redisService.getAndSet(key + ".lock", Constants.REDIS_KEY_EXPIRED_LOCK);
            if (!Objects.equals(Constants.REDIS_KEY_LOCK, oldLock)) {
                return;
            }
            log.info("达到业务设置对应失效时间设定,进入回调的key:{}", key);
            /**
             * 获取对应的sourceCode,并实例化子类
             */
            String cutCommonPrefix = key.replace(Constants.REDIS_KEY_PREFIX, "");
            String sourceCode = cutCommonPrefix.substring(0, cutCommonPrefix.indexOf(Constants.REDIS_KEY_CONNECT));
            String primaryId = key.replace(Constants.REDIS_KEY_PREFIX + sourceCode + Constants.REDIS_KEY_CONNECT, "");
            AbstractExpiredCallBackTemplate expiredCallBackTemplate = ClassUtil.getChildClass(sourceCode);
            /**
             * 校验是否已经回调完成
             */
            if (expiredCallBackTemplate.checkAlreadyCallBackIfNecessary(primaryId)) {
                return;
            }
            expiredCallBackTemplate.doBusiness(primaryId);
        } catch (Exception e) {
            log.error("redis key失效回调出现异常,对应key为" + key, e);
            e.printStackTrace();
        } finally {
            redisService.del(key + ".lock");
        }
    }
}
