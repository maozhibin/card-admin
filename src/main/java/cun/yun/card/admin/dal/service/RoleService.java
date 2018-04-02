package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.dto.RoleDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Role;

import java.util.List;

public interface RoleService {
    void roleList(Page<Role> page, String name);

    List<MenuDto> queryMenuByRoleId(Long id);

    Role queryByRoleName(String name);

    void insert(Role role);

    Role queryById(Long id);

    void update(Role role);

    void deleteById(String roleId);

    List<Role> queryALL();
}
