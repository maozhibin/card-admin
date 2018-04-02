package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.RoleMenuMapper;
import cun.yun.card.admin.dal.model.RoleMenu;
import cun.yun.card.admin.dal.service.RoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void insertList(List<RoleMenu> roleMenus) {
        roleMenuMapper.insertList(roleMenus);
    }

    @Override
    public void deleteByRoleId(Long id) {
        roleMenuMapper.deleteByRoleId(id);
    }

    @Override
    public void deleteByMenuId(Long id) {
        roleMenuMapper.deleteByMenuId(id);
    }
}
