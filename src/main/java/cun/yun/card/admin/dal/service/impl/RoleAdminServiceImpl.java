package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.RoleAdminMapper;
import cun.yun.card.admin.dal.dao.RoleMenuMapper;
import cun.yun.card.admin.dal.model.RoleAdmin;
import cun.yun.card.admin.dal.service.RoleAdminService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleAdminServiceImpl implements RoleAdminService {
    @Resource
    private RoleAdminMapper roleAdminMapper;

    @Override
    public RoleAdmin queryByRoleId(Long roleId) {
        return roleAdminMapper.queryByRoleId(roleId);
    }
}
