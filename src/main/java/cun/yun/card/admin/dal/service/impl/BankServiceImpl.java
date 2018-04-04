package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.BankLinkMapper;
import cun.yun.card.admin.dal.dao.BankMapper;
import cun.yun.card.admin.dal.dto.BankDto;
import cun.yun.card.admin.dal.dto.BankLinkDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bank;
import cun.yun.card.admin.dal.service.BankService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BankServiceImpl implements BankService {
    @Resource
    private BankMapper bankMapper;
    @Resource
    private BankLinkMapper bankLinkMapper;

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

    @Override
    public Bank queryByBankName(String name) {
        return bankMapper.queryByBankName(name);
    }

    @Override
    public void insert(Bank bank) {
        bankMapper.insertSelective(bank);
    }

    @Override
    public Integer queryMaxSort() {
        return bankMapper.queryMaxSort();
    }

    @Override
    public void bankLinkList(Page<BankLinkDto> page, Long bankId) {
        int total = bankLinkMapper.totalCount(bankId);
        page.setTotal(total);
        page.setRows(bankLinkMapper.bankLinkList(page.getOffset(),page.getLimit(), bankId));
    }


}
