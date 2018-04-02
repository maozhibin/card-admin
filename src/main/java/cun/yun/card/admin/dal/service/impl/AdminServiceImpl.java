package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.AdminMapper;
import cun.yun.card.admin.dal.dto.AdminDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Admin;
import cun.yun.card.admin.dal.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin queryByUserPass(String userName, String password) {
        return adminMapper.queryByUserPass(userName,password);
    }

    @Override
    public void adminList(Page<AdminDto> page, String name) {
        int total = adminMapper.totalCount(name);
        page.setTotal(total);
        page.setRows(adminMapper.adminList(page.getOffset(),page.getLimit(),name));
    }

    @Override
    public void update(Admin admin1) {
        adminMapper.updateByPrimaryKeySelective(admin1);
    }

    @Override
    public void insert(Admin admin) {
        adminMapper.insertSelective(admin);
    }

    @Override
    public Admin queryByUserName(String userName) {
        return adminMapper.queryByUserName(userName);
    }

    @Override
    public Admin queryById(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}
