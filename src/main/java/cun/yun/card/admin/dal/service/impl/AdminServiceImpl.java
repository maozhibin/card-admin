package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.AdminMapper;
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
}
