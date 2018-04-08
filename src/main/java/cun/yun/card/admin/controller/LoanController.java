package cun.yun.card.admin.controller;

import cun.yun.card.admin.common.CommonConstant;
import cun.yun.card.admin.dal.dao.CooperativeLinkProductMapper;
import cun.yun.card.admin.dal.dao.LoanLinkMapper;
import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.dto.LoanLinkDto;
import cun.yun.card.admin.dal.dto.SortLoan;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bond;
import cun.yun.card.admin.dal.model.CooperativeLinkProduct;
import cun.yun.card.admin.dal.model.Loan;
import cun.yun.card.admin.dal.model.LoanLink;
import cun.yun.card.admin.dal.service.LoanLinkService;
import cun.yun.card.admin.dal.service.LoanService;
import cun.yun.card.admin.util.JsonResponseMsg;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("loan")
public class LoanController {

    @Resource
    private LoanService loanService;
    @Resource
    private LoanLinkService loanLinkService;
    @Resource
    private CooperativeLinkProductMapper cooperativeLinkProductMapper;

    /**
     * 现金贷列表
     * @param limit
     * @param offset
     * @param name
     * @return
     */
    @RequestMapping(value = "loanList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg loanList(@RequestParam(defaultValue = "10", required = false) Integer limit,
                                    @RequestParam(defaultValue = "1", required = false) Integer offset, String name){
        JsonResponseMsg result = new JsonResponseMsg();
        Page<Loan> page = new Page<>(limit, offset);
        loanService.loanList(page,name);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }

    /**
     * 修现金贷排名
     */
    @RequestMapping(value = "updateSort",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateSort(@RequestBody SortLoan sortLoan){
        JsonResponseMsg result = new JsonResponseMsg();
        if(sortLoan.getNextId()==null||sortLoan.getTopId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"参数错误");
        }

        Loan LoanTop = loanService.queryByBankId(sortLoan.getTopId());
        if(LoanTop==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"修改的证券不存在");
        }
        Loan LoanNext = loanService.queryByBankId(sortLoan.getNextId());
        if(LoanNext==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"修改的证券不存在");
        }
        Integer topSort = LoanTop.getSort();
        Integer nextSort = LoanNext.getSort();
        LoanTop.setSort(nextSort);
        LoanNext.setSort(topSort);
        loanService.update(LoanTop);
        loanService.update(LoanNext);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改排名成功");
    }


    /**
     * 现金贷添加
     */
    @RequestMapping(value = "addLoan",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addLoan(@RequestBody Loan loan){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(loan.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入现金贷名称");
        }
        if(StringUtils.isEmpty(loan.getImage())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"传入现金贷图片");
        }
        if(loan.getLimitMax()==null || loan.getLimitMin()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入正确的额度");
        }
        if(loan.getMoneyRate()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入正确的利率");
        }

        Loan loan1 = loanService.queryByLoanName(loan.getName());
        if(loan1!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你输入的现金贷名称已经存在，请重新输入");
        }

        loan.setIsEmploy(CommonConstant.IS_EMPLOY);
        loan.setUpdatedTime(new Date());
        loan.setCreateTime(new Date());
        Integer maxSort = loanService.queryMaxSort();
        loan.setSort(maxSort+1);
        loanService.insert(loan);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加现金贷成功");
    }

    /**
     * 现金贷修改
     */
    @RequestMapping(value = "updateLoan",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateLoan(@RequestBody Loan loan){
        JsonResponseMsg result = new JsonResponseMsg();
        if(loan.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入Id");
        }
        if(loan.getLimitMax()==null || loan.getLimitMin()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入正确的额度");
        }
        if(loan.getMoneyRate()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入正确的利率");
        }

        if(StringUtils.isEmpty(loan.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入现金贷名称");
        }
        if(StringUtils.isEmpty(loan.getImage())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"传入现金贷图片");
        }

        Loan loan1 = loanService.queryByLoanId(loan.getId());
        if(loan1==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要修改的现金贷不存在");
        }else{
            if(loan1.getName().equals(loan.getName())){
                Loan loan2 = loanService.queryByLoanName(loan.getName());
                if(loan2!=null){
                    return result.fill(JsonResponseMsg.CODE_FAIL,"你输入的现金贷名称已经存在，请重新输入");
                }
            }
        }
        loan.setUpdatedTime(new Date());
        loanService.insert(loan);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改现金贷成功");
    }

    /**
     * 现金贷删除
     */
    @RequestMapping(value = "deleteLoan",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteBond(String loanId){
        JsonResponseMsg result = new JsonResponseMsg();
        if(NumberUtils.isNumber(loanId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入需要删除的现金贷Id");
        }
        List<LoanLink> list = loanLinkService.queryByLoanId(NumberUtils.toLong(loanId));
        if(list.size()==0){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要删除的现金贷还有链接在使用，暂时不能进行删除");
        }
        Loan loan = loanService.selectByPrimaryKey(NumberUtils.toLong(loanId));
        if(loan==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要删除的证券不存在");
        }
        loan.setIsEmploy(CommonConstant.NO_EMPLOY);
        loanService.update(loan);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除成功");
    }

    /**
     * 现金贷链接列表
     */
    @RequestMapping(value = "loanLinkList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg loanLinkList(@RequestParam(defaultValue = "10", required = false) Integer limit,
                                        @RequestParam(defaultValue = "1", required = false) Integer offset, String loanId){
        JsonResponseMsg result = new JsonResponseMsg();

        if(!NumberUtils.isNumber(loanId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入现金贷Id");
        }
        Page<LoanLinkDto> page = new Page<>(limit, offset);
        loanLinkService.loanLinkList(page,NumberUtils.toLong(loanId));
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }

    /**
     * 添加现金贷链接
     */
    @RequestMapping(value = "addLoanLink",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addLoanLink(@RequestBody LoanLink loanLink){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(loanLink.getUrl())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Url");
        }
        if(loanLink.getLoanId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入现金贷Id");
        }
        loanLink.setIsEmploy(CommonConstant.IS_EMPLOY);
        loanLink.setCreatedTime(new Date());
        loanLink.setUpdatedTime(new Date());
        loanLinkService.insertSelective(loanLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加链接成功");
    }

    /**
     * 修改现金贷链接
     */
    @RequestMapping(value = "updateLoanLink",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateLoanLink(@RequestBody LoanLink loanLink){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(loanLink.getUrl())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Url");
        }
        if(loanLink.getLoanId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行Id");
        }
        if(loanLink.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Id");
        }
        loanLink.setUpdatedTime(new Date());
        loanLinkService.updateByPrimaryKeySelective(loanLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改链接成功");
    }
    /**
     * 删除证券链接
     */
    @RequestMapping(value = "deleteBondLinkById",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteLoanLinkById(String LoanLinkId){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(LoanLinkId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Id");
        }
        LoanLink loanLink = loanLinkService.selectByPrimaryKey(NumberUtils.toLong(LoanLinkId));
        if(loanLink==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要删除的现金贷链接不存在");
        }
        CooperativeLinkProduct cooperativeLinkProduct =cooperativeLinkProductMapper.queryByLoanLinkId(NumberUtils.toLong(LoanLinkId));
        if(cooperativeLinkProduct!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"链接有客户正在使用，不能进行删除");
        }
        loanLink.setIsEmploy(CommonConstant.NO_EMPLOY);
        loanLinkService.updateByPrimaryKeySelective(loanLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除成功");
    }
}
