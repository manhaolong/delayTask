package com.ismarthealth.delaytask.server.mapper;

import com.ismarthealth.delaytask.server.pojo.DelayTaskLogModel;
import com.ismarthealth.delaytask.server.pojo.DelayTaskLogModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DelayTaskLogModelMapper {
    int countByExample(DelayTaskLogModelExample example);

    int deleteByExample(DelayTaskLogModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DelayTaskLogModel record);

    int insertSelective(DelayTaskLogModel record);

    List<DelayTaskLogModel> selectByExample(DelayTaskLogModelExample example);

    DelayTaskLogModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DelayTaskLogModel record, @Param("example") DelayTaskLogModelExample example);

    int updateByExample(@Param("record") DelayTaskLogModel record, @Param("example") DelayTaskLogModelExample example);

    int updateByPrimaryKeySelective(DelayTaskLogModel record);

    int updateByPrimaryKey(DelayTaskLogModel record);

    DelayTaskLogModel selectOneByExample(DelayTaskLogModelExample example);
}