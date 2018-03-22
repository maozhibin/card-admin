package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.CooperativeProxyPrice;

public interface CooperativeProxyPriceMapper {
    int insert(CooperativeProxyPrice record);

    int insertSelective(CooperativeProxyPrice record);

    CooperativeProxyPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CooperativeProxyPrice record);

    int updateByPrimaryKey(CooperativeProxyPrice record);
}