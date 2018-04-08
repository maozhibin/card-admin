package cun.yun.card.admin.dal.service;


import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Loan;

public interface LoanService {
    void loanList(Page<Loan> page, String name);

    Loan queryByBankId(Long topId);

    void update(Loan loanTop);

    Loan queryByLoanName(String name);

    Integer queryMaxSort();

    void insert(Loan loan);

    Loan queryByLoanId(Long id);

    Loan selectByPrimaryKey(long l);
}
