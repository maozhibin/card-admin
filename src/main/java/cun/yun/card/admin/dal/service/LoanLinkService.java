package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.dto.LoanLinkDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.LoanLink;

import java.util.List;

public interface LoanLinkService {
    List<LoanLink> queryByLoanId(Long l);

    void loanLinkList(Page<LoanLinkDto> page, Long loanId);

    void insertSelective(LoanLink loanLink);

    void updateByPrimaryKeySelective(LoanLink loanLink);

    LoanLink selectByPrimaryKey(long l);
}
