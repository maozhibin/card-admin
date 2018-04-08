package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.dto.BondLinkDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bond;

public interface BondService {
    void bondList(Page<BondDto> page, String name);

    Bond queryByBankId(Long topId);

    void update(Bond bondTop);

    Bond queryByBondName(String name);

    Integer queryMaxSort();

    void insert(Bond bond);

    Bond selectByPrimaryKey(Long bondId);

    void bondLinkList(Page<BondLinkDto> page, Long bondId);
}
