package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.dto.BankLinkDto;
import cun.yun.card.admin.dal.model.BankLink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankLinkMapper {
    int insert(BankLink record);

    int insertSelective(BankLink record);

    BankLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankLink record);

    int updateByPrimaryKey(BankLink record);

    List<BankLinkDto> bankLinkList(@Param("begin") int offset, @Param("end")int limit, @Param("bankId") Long bankId);

    int totalCount(@Param("bankId") Long bankId);

    List<BankLink> queryByBankId(@Param("bankId") Long bankId);
}