package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.model.Loan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoanMapper {
    int insert(Loan record);

    int insertSelective(Loan record);

    Loan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Loan record);

    int updateByPrimaryKey(Loan record);

    int totalCount(@Param("name")String name);

    List<Loan> loanList(@Param("begin") int offset, @Param("end")int limit, @Param("name") String name);

    Loan queryByLoanName(@Param("name")String name);

    Integer queryMaxSort();
}