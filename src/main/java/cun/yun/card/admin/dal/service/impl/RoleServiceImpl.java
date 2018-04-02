package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.common.CommonConstant;
import cun.yun.card.admin.dal.dao.RoleMapper;
import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.dto.RoleDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Role;
import cun.yun.card.admin.dal.service.RoleService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
    public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public void roleList(Page<Role> page, String name) {
        int total = roleMapper.totalCount(name);
        page.setTotal(total);
        page.setRows(roleMapper.roleList(page.getOffset(),page.getLimit(),name));
    }

    @Override
    public List<MenuDto> queryMenuByRoleId(Long id) {
        return roleMapper.queryMenuByRoleId(id);
    }

    @Override
    public Role queryByRoleName(String name) {
        return roleMapper.queryByRoleName(name);
    }

    @Override
    public void insert(Role role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public Role queryById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Role role) {
         roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteById(String roleId) {
        Role role = new Role();
        role.setIsEmploy(CommonConstant.NO_EMPLOY);
        role.setId(NumberUtils.toLong(roleId));
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public List<Role> queryALL() {
        return roleMapper.queryALL();
    }
}
