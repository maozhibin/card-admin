package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.BankMapper;
import cun.yun.card.admin.dal.dto.BankDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bank;
import cun.yun.card.admin.dal.service.BankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BankServiceImpl implements BankService {
    @Resource
    private BankMapper bankMapper;

    @Override
    public void bankList(Page<BankDto> page, String name) {
        int total = bankMapper.totalCount(name);
        page.setTotal(total);
        page.setRows(bankMapper.bankList(page.getOffset(),page.getLimit(),name));
    }

    @Override
    public Bank queryByBankId(Long topId) {
        return bankMapper.selectByPrimaryKey(topId);
    }

    @Override
    public void update(Bank bankTop) {
        bankMapper.updateByPrimaryKeySelective(bankTop);
    }
}
