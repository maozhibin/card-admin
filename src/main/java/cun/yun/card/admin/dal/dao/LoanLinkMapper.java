package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.dto.LoanLinkDto;
import cun.yun.card.admin.dal.model.LoanLink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoanLinkMapper {
    int insert(LoanLink record);

    int insertSelective(LoanLink record);

    LoanLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanLink record);

    int updateByPrimaryKey(LoanLink record);

    int totalCount(@Param("loanId")Long loanId);

    List<LoanLink> queryByLoanId(@Param("loanId") Long loanId);

    List<LoanLinkDto> loanLinkList(@Param("begin") int offset, @Param("end")int limit, @Param("loanId") Long loanId);
}