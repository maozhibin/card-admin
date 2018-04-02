package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.dto.AdminDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Admin;

public interface AdminService {
    Admin queryByUserPass(String userName, String password);

    void adminList(Page<AdminDto> page, String name);

    void update(Admin admin1);

    void insert(Admin admin);

    Admin queryByUserName(String userName);

    Admin queryById(Long id);
}
