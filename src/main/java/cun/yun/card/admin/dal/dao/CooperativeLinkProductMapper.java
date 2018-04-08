package cun.yun.card.admin.dal.dao;

import cun.yun.card.admin.dal.model.CooperativeLinkProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperativeLinkProductMapper {
    int insert(CooperativeLinkProduct record);

    int insertSelective(CooperativeLinkProduct record);

    CooperativeLinkProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CooperativeLinkProduct record);

    int updateByPrimaryKey(CooperativeLinkProduct record);

    CooperativeLinkProduct queryByBankLinkId(@Param("bankId") Long bankId);

    CooperativeLinkProduct queryByBondLinkId(@Param("bondId") Long bondId);

    CooperativeLinkProduct queryByLoanLinkId(@Param("LoanLinkId") Long LoanLinkId);

    void insertList(List<CooperativeLinkProduct> cooperativeLinkProducts);
}