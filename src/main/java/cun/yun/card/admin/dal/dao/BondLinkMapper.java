package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.BondLinkDto;
import cun.yun.card.admin.dal.model.BondLink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BondLinkMapper {
    int insert(BondLink record);

    int insertSelective(BondLink record);

    BondLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BondLink record);

    int updateByPrimaryKey(BondLink record);

    List<BondLink> queryByBondId(Long bondId);

    int totalCount(@Param("bondId") long bondId);

    List<BondLinkDto> bondLinkList(@Param("begin") int offset, @Param("end")int limit, @Param("bondId") Long bondId);
}