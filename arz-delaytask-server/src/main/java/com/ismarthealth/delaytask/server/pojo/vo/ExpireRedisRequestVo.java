package com.ismarthealth.delaytask.server.pojo.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 延时任务参数请求vo
 * @author: Liuxk
 * @create: 2020-01-16 09:29
 **/
public class ExpireRedisRequestVo {
    @ApiModelProperty("区分不同的业务逻辑")
    private String sourceCode;
    @ApiModelProperty("不同的业务逻辑对应数据库主键id")
    private String id;
    @ApiModelProperty("业务提供的失效时间,非必传")
    private Long expireTime;

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "ExpireRedisRequestVo{" +
                "sourceCode='" + sourceCode + '\'' +
                ", id='" + id + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
