package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.ApplayUserInfo;

public interface ApplayUserInfoMapper {
    int insert(ApplayUserInfo record);

    int insertSelective(ApplayUserInfo record);

    ApplayUserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplayUserInfo record);

    int updateByPrimaryKey(ApplayUserInfo record);
}