package com.ismarthealth.delaytask.server.template.impl;

import com.ismarthealth.delaytask.server.common.annotation.ExpiredCallBackConfig;
import com.ismarthealth.delaytask.server.common.eum.ConsultationStatusEnum;
import com.ismarthealth.delaytask.server.mapper.ConsultationModelMapper;
import com.ismarthealth.delaytask.server.template.AbstractExpiredCallBackTemplate;
import com.ismarthealth.osp.core.common.pojo.to.ConsultationModel;
import com.ismarthealth.osp.core.common.pojo.to.ConsultationModelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 会话模板子类
 * @author: Liuxk
 * @create: 2020-01-04 16:14
 **/
@Service
@ExpiredCallBackConfig(sourceCode = "aa")
public class ConsultationExpiredCallBackTemplateImpl extends AbstractExpiredCallBackTemplate {

    @Autowired
    ConsultationModelMapper consultationModelMapper;

    @Override
    public Boolean checkAlreadyCallBackIfNecessary(String primaryId) {
        ConsultationModel consultationModel = getConsultationModelByPrimaryKey(primaryId);
        Integer status = consultationModel.getStatus();
        return status.equals(ConsultationStatusEnum.NOTENABLED.getIndex());
    }


    @Override
    public void doBusiness(String primaryId) {
        ConsultationModel consultationModel = getConsultationModelByPrimaryKey(primaryId);
        consultationModel.setStatus(ConsultationStatusEnum.NOTENABLED.getIndex());
        ConsultationModelExample example = new ConsultationModelExample();
        ConsultationModelExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(ConsultationStatusEnum.ENABLED.getIndex());
        criteria.andIdEqualTo(primaryId);
        consultationModelMapper.updateByExampleSelective(consultationModel, example);
    }

    /**
     * 通过主键查询对应的会话
     *
     * @param consultationId
     * @return
     */
    private ConsultationModel getConsultationModelByPrimaryKey(String consultationId) {
        ConsultationModel consultationModel = consultationModelMapper.selectByPrimaryKey(consultationId);
        return consultationModel;
    }
}
