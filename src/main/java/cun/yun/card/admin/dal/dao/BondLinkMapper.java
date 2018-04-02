package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.BondLink;

public interface BondLinkMapper {
    int insert(BondLink record);

    int insertSelective(BondLink record);

    BondLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BondLink record);

    int updateByPrimaryKey(BondLink record);
}