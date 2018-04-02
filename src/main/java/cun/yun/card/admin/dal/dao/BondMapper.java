package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.Bond;

public interface BondMapper {
    int insert(Bond record);

    int insertSelective(Bond record);

    Bond selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bond record);

    int updateByPrimaryKeyWithBLOBs(Bond record);

    int updateByPrimaryKey(Bond record);
}