package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.Loan;

public interface LoanMapper {
    int insert(Loan record);

    int insertSelective(Loan record);

    Loan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Loan record);

    int updateByPrimaryKey(Loan record);
}