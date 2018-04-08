package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.AdminCooperativePartner;

public interface AdminCooperativePartnerMapper {
    int insert(AdminCooperativePartner record);

    int insertSelective(AdminCooperativePartner record);

    AdminCooperativePartner selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminCooperativePartner record);

    int updateByPrimaryKey(AdminCooperativePartner record);
}