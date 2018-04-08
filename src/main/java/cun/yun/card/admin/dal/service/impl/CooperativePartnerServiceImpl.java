package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.CooperativePartnerMapper;
import cun.yun.card.admin.dal.model.CooperativePartner;
import cun.yun.card.admin.dal.service.CooperativePartnerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CooperativePartnerServiceImpl implements CooperativePartnerService {
    @Resource
    private CooperativePartnerMapper cooperativePartnerMapper;

    @Override
    public CooperativePartner queryByAdminId(Long adminId) {
        return cooperativePartnerMapper.queryByAdminId(adminId);
    }
}
