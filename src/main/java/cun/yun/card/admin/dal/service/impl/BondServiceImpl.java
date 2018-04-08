package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.BondLinkMapper;
import cun.yun.card.admin.dal.dao.BondMapper;
import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.dto.BondLinkDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bond;
import cun.yun.card.admin.dal.service.BondService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BondServiceImpl implements BondService {
    @Resource
    private BondMapper bondMapper;
    @Resource
    private BondLinkMapper bondLinkMapper;

    @Override
    public void bondList(Page<BondDto> page, String name) {
        int total = bondMapper.totalCount(name);
        page.setTotal(total);
        page.setRows(bondMapper.bondList(page.getOffset(),page.getLimit(),name));

    }

    @Override
    public Bond queryByBankId(Long topId) {
        return bondMapper.selectByPrimaryKey(topId);
    }

    @Override
    public void update(Bond bondTop) {
        bondMapper.updateByPrimaryKeySelective(bondTop);
    }

    @Override
    public Bond queryByBondName(String name) {
        return bondMapper.queryByBondName(name);
    }

    @Override
    public Integer queryMaxSort() {
        return bondMapper.queryMaxSort();
    }

    @Override
    public void insert(Bond bond) {
        bondMapper.insert(bond);
    }

    @Override
    public Bond selectByPrimaryKey(Long bondId) {
        return bondMapper.selectByPrimaryKey(bondId);
    }

    @Override
    public void bondLinkList(Page<BondLinkDto> page, Long bondId) {
        int total = bondLinkMapper.totalCount(bondId);
        page.setTotal(total);
        page.setRows(bondLinkMapper.bondLinkList(page.getOffset(),page.getLimit(), bondId));
    }
}
