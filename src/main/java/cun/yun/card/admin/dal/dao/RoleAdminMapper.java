package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.RoleAdmin;

public interface RoleAdminMapper {
    int insert(RoleAdmin record);

    int insertSelective(RoleAdmin record);

    RoleAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleAdmin record);

    int updateByPrimaryKey(RoleAdmin record);

    RoleAdmin queryByRoleId(Long roleId);
}