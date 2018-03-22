package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.BankLink;

public interface BankLinkMapper {
    int insert(BankLink record);

    int insertSelective(BankLink record);

    BankLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankLink record);

    int updateByPrimaryKey(BankLink record);
}