package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.model.RoleMenu;

import java.util.List;

public interface RoleMenuService {
    void insertList(List<RoleMenu> roleMenus);

    void deleteByRoleId(Long id);

    void deleteByMenuId(Long id);
}
