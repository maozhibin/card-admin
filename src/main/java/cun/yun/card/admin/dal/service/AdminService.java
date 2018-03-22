package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.model.Admin;

public interface AdminService {
    Admin queryByUserPass(String userName, String password);
}
