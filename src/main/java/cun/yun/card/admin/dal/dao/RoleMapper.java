package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    int totalCount(@Param("name") String name);

    List<Role> roleList(@Param("begin") int offset, @Param("end")int limit,@Param("name") String name);

    List<MenuDto> queryMenuByRoleId(Long id);

    Role queryByRoleName(@Param("name") String name);

    void deleteById(Long roleId);

    List<Role> queryALL();
}