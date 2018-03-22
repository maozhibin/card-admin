package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.dto.MenuDto;

import java.util.List;

public interface MenuService {
    List<MenuDto> queryByAdminIdAndParentId(Integer adminId);

    List<MenuDto> queryByAdminId(Integer adminId);
}
