package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.ClickLink;

public interface ClickLinkMapper {
    int insert(ClickLink record);

    int insertSelective(ClickLink record);

    ClickLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClickLink record);

    int updateByPrimaryKey(ClickLink record);
}