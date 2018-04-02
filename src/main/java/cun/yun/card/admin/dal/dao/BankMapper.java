package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.BankDto;
import cun.yun.card.admin.dal.model.Bank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankMapper {
    int insert(Bank record);

    int insertSelective(Bank record);

    Bank selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bank record);

    int updateByPrimaryKeyWithBLOBs(Bank record);

    int updateByPrimaryKey(Bank record);

    int totalCount(@Param("name") String name);

    List<BankDto> bankList(@Param("begin") int offset, @Param("end")int limit, @Param("name") String name);
}