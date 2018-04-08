package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.CooperatePartnerStatisticsDay;
import cun.yun.card.admin.dal.model.CooperativeLinkProduct;
import org.apache.ibatis.annotations.Param;

public interface CooperatePartnerStatisticsDayMapper {
    int insert(CooperatePartnerStatisticsDay record);

    int insertSelective(CooperatePartnerStatisticsDay record);

    CooperatePartnerStatisticsDay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CooperatePartnerStatisticsDay record);

    int updateByPrimaryKey(CooperatePartnerStatisticsDay record);
}