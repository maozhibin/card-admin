package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.BondMapper;
import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bond;
import cun.yun.card.admin.dal.service.BondService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BondServiceImpl implements BondService {
    @Resource
    private BondMapper bondMapper;

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
}
