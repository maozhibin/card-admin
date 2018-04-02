package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.AdminDto;
import cun.yun.card.admin.dal.model.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin queryByUserPass(@Param("userName") String userName, @Param("password")String password);

    int totalCount(@Param("name")String name);

    List<AdminDto> adminList(@Param("begin") int offset, @Param("end")int limit,@Param("name") String name);

    Admin queryByUserName(@Param("userName")String userName);
}