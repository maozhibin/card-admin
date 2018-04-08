package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.LoanLinkMapper;
import cun.yun.card.admin.dal.dao.LoanMapper;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Loan;
import cun.yun.card.admin.dal.service.LoanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoanServiceImpl implements LoanService {
    @Resource
    private LoanLinkMapper loanLinkMapper;
    @Resource
    private LoanMapper loanMapper;

    @Override
    public void loanList(Page<Loan> page, String name) {
          int total = loanMapper.totalCount(name);
          page.setTotal(total);
          page.setRows(loanMapper.loanList(page.getOffset(),page.getLimit(),name));
    }

    @Override
    public Loan queryByBankId(Long topId) {
        return loanMapper.selectByPrimaryKey(topId);
    }

    @Override
    public void update(Loan loanTop) {
        loanMapper.updateByPrimaryKeySelective(loanTop);
    }

    @Override
    public Loan queryByLoanName(String name) {
        return loanMapper.queryByLoanName(name);
    }

    @Override
    public Integer queryMaxSort() {
        return loanMapper.queryMaxSort();
    }

    @Override
    public void insert(Loan loan) {
        loanMapper.insertSelective(loan);
    }

    @Override
    public Loan queryByLoanId(Long id) {
        return loanMapper.selectByPrimaryKey(id);
    }

    @Override
    public Loan selectByPrimaryKey(long id) {
        return loanMapper.selectByPrimaryKey(id);
    }
}
