package cun.yun.card.admin.dal.service;

import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Menu;

import java.util.List;

public interface MenuService {
    List<MenuDto> queryByAdminIdAndParentId(Integer adminId);

    List<MenuDto> queryByAdminId(Integer adminId);

    List<MenuDto> queryAllInfo();

    List<MenuDto> getChild(Long id, List<MenuDto> rootMenu);

    void addMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(String id);

    List<MenuDto> queryByParentId(Long parentId);
}
