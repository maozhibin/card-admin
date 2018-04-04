package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.common.CommonConstant;
import cun.yun.card.admin.dal.dao.MenuMapper;
import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Menu;
import cun.yun.card.admin.dal.service.MenuService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuDto> queryByAdminIdAndParentId(Integer adminId) {
        return menuMapper.queryByAdminIdAndParentId(adminId);
    }

    @Override
    public List<MenuDto> queryByAdminId(Integer adminId) {
        return menuMapper.queryByAdminId(adminId);
    }

    @Override
    public List<MenuDto> queryAllInfo() {
        return menuMapper.queryAllInfo();
    }

    @Override
    public void addMenu(Menu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void deleteMenu(String id) {
        Menu menu = new Menu();
        menu.setId(NumberUtils.toLong(id));
        menu.setIsEmploy(CommonConstant.NO_EMPLOY);
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public List<MenuDto> queryByParentId(Long parentId) {
        return menuMapper.queryByParentId(parentId);
    }


    /**
     * 递归查找子菜单
     * 当前菜单id
     * 要查找的列表
     * @return
     */
    @Override
    public List<MenuDto> getChild(Long id, List<MenuDto> rootMenu) {
        // 子菜单
        List<MenuDto> childList = new ArrayList<>();
        for (MenuDto menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId()!=null) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (MenuDto menu : childList) {
            // 递归
            menu.setMenulist(getChild(menu.getId(), rootMenu));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }



}
