package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<MenuDto> queryByAdminIdAndParentId(@Param("adminId") Integer adminId);

    List<MenuDto> queryByAdminId(@Param("adminId") Integer adminId);
}