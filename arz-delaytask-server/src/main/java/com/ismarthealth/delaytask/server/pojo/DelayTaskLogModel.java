package com.ismarthealth.delaytask.server.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class DelayTaskLogModel {
    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("业务类型")
    private String businessId;

    @ApiModelProperty("设备编号")
    private String businessType;

    @ApiModelProperty("状态 1 初始化 2延时成功")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" {");
        sb.append(", id=").append(id);
        sb.append(", businessId=").append(businessId);
        sb.append(", businessType=").append(businessType);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append("}");
        return sb.toString();
    }
}