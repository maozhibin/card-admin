package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.LoanLinkMapper;
import cun.yun.card.admin.dal.dto.LoanLinkDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.LoanLink;
import cun.yun.card.admin.dal.service.LoanLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoanLinkServiceImpl implements LoanLinkService {
    @Resource
    private LoanLinkMapper loanLinkMapper;

    @Override
    public List<LoanLink> queryByLoanId(Long loanId) {
        return loanLinkMapper.queryByLoanId(loanId);
    }

    @Override
    public void loanLinkList(Page<LoanLinkDto> page, Long loanId) {
        int total = loanLinkMapper.totalCount(loanId);
        page.setTotal(total);
        page.setRows(loanLinkMapper.loanLinkList(page.getOffset(),page.getLimit(), loanId));
    }

    @Override
    public void insertSelective(LoanLink loanLink) {
        loanLinkMapper.insertSelective(loanLink);
    }

    @Override
    public void updateByPrimaryKeySelective(LoanLink loanLink) {
        loanLinkMapper.updateByPrimaryKeySelective(loanLink);
    }

    @Override
    public LoanLink selectByPrimaryKey(long l) {
        return loanLinkMapper.selectByPrimaryKey(l);
    }


}
