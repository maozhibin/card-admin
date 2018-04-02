package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.RoleAdmin;
import cun.yun.card.admin.dal.model.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper {
    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);

    void insertList(@Param("roleMenus") List<RoleMenu> roleMenus);

    void deleteByRoleId(Long id);

    void deleteByMenuId(Long id);

    RoleAdmin queryByRoleId(Long id);
}