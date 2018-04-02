package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.model.RoleAdmin;

public interface RoleAdminService {
    RoleAdmin queryByRoleId(Long roleId);
}
