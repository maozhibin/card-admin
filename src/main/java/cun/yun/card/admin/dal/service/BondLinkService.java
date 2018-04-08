package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.model.BondLink;

import java.util.List;

public interface BondLinkService {
    List<BondLink> queryByBondId(Long bondId);

    void insertSelective(BondLink bondLink);

    void updateByPrimaryKeySelective(BondLink bondLink);

    BondLink selectByPrimaryKey(Long bondId);
}
