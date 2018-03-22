package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin queryByUserPass(@Param("userName") String userName, @Param("password")String password);
}