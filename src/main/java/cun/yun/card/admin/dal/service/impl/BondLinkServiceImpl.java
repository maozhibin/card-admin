package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.BondLinkMapper;
import cun.yun.card.admin.dal.model.BondLink;
import cun.yun.card.admin.dal.service.BondLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BondLinkServiceImpl implements BondLinkService {
    @Resource
    private BondLinkMapper bondLinkMapper;

    @Override
    public List<BondLink> queryByBondId(Long bondId) {
        return bondLinkMapper.queryByBondId(bondId);
    }

    @Override
    public void insertSelective(BondLink bondLink) {
        bondLinkMapper.insertSelective(bondLink);
    }

    @Override
    public void updateByPrimaryKeySelective(BondLink bondLink) {
        bondLinkMapper.updateByPrimaryKeySelective(bondLink);
    }

    @Override
    public BondLink selectByPrimaryKey(Long bondId) {
        return bondLinkMapper.selectByPrimaryKey(bondId);
    }
}
