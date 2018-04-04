package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.model.Bond;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BondMapper {
    int insert(Bond record);

    int insertSelective(Bond record);

    Bond selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bond record);

    int updateByPrimaryKeyWithBLOBs(Bond record);

    int updateByPrimaryKey(Bond record);

    int totalCount(@Param("name") String name);

    List<BondDto> bondList(@Param("begin") int offset, @Param("end")int limit, @Param("name") String name);

}