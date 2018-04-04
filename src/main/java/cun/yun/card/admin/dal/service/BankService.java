package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.dto.BankDto;
import cun.yun.card.admin.dal.dto.BankLinkDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bank;

public interface BankService {
    void bankList(Page<BankDto> page, String name);

    Bank queryByBankId(Long topId);

    void update(Bank bankTop);

    Bank queryByBankName(String name);

    void insert(Bank bank);

    Integer queryMaxSort();

    void bankLinkList(Page<BankLinkDto> page, Long bankId);
}
