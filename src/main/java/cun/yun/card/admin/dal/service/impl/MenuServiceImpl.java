package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.MenuMapper;
import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


}
