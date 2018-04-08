package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.CooperativePartnerLink;

public interface CooperativePartnerLinkMapper {
    int insert(CooperativePartnerLink record);

    int insertSelective(CooperativePartnerLink record);

    CooperativePartnerLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CooperativePartnerLink record);

    int updateByPrimaryKey(CooperativePartnerLink record);

    int insertLink(CooperativePartnerLink cooperativePartnerLink);
}