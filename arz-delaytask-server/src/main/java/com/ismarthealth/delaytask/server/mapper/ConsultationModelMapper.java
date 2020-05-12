package com.ismarthealth.delaytask.server.mapper;

import com.ismarthealth.osp.core.common.pojo.to.ConsultationModel;
import com.ismarthealth.osp.core.common.pojo.to.ConsultationModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsultationModelMapper {
    int countByExample(ConsultationModelExample example);

    int deleteByExample(ConsultationModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ConsultationModel record);

    int insertSelective(ConsultationModel record);

    List<ConsultationModel> selectByExample(ConsultationModelExample example);

    ConsultationModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ConsultationModel record, @Param("example") ConsultationModelExample example);

    int updateByExample(@Param("record") ConsultationModel record, @Param("example") ConsultationModelExample example);

    int updateByPrimaryKeySelective(ConsultationModel record);

    int updateByPrimaryKey(ConsultationModel record);

    ConsultationModel selectOneByExample(ConsultationModelExample example);
}