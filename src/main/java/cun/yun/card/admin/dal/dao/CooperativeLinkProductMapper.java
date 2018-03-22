package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.CooperativeLinkProduct;

public interface CooperativeLinkProductMapper {
    int insert(CooperativeLinkProduct record);

    int insertSelective(CooperativeLinkProduct record);

    CooperativeLinkProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CooperativeLinkProduct record);

    int updateByPrimaryKey(CooperativeLinkProduct record);
}