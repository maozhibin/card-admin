package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bond;

public interface BondService {
    void bondList(Page<BondDto> page, String name);

    Bond queryByBankId(Long topId);

    void update(Bond bondTop);
}
