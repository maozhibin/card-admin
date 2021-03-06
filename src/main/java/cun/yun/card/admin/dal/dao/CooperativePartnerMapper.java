package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.CooperativePartner;
import org.apache.ibatis.annotations.Param;

public interface CooperativePartnerMapper {
    int insert(CooperativePartner record);

    int insertSelective(CooperativePartner record);

    CooperativePartner selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CooperativePartner record);

    int updateByPrimaryKeyWithBLOBs(CooperativePartner record);

    int updateByPrimaryKey(CooperativePartner record);

    CooperativePartner queryByAdminId(@Param("adminId") Long adminId);
}