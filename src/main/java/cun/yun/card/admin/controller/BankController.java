package cun.yun.card.admin.controller;

import cun.yun.card.admin.common.CommonConstant;
import cun.yun.card.admin.dal.dao.BankLinkMapper;
import cun.yun.card.admin.dal.dao.CooperatePartnerStatisticsDayMapper;
import cun.yun.card.admin.dal.dao.CooperativeLinkProductMapper;
import cun.yun.card.admin.dal.dto.BankDto;
import cun.yun.card.admin.dal.dto.BankLinkDto;

import cun.yun.card.admin.dal.dto.SortDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bank;
import cun.yun.card.admin.dal.model.BankLink;
import cun.yun.card.admin.dal.model.CooperativeLinkProduct;

import cun.yun.card.admin.dal.service.BankService;
import cun.yun.card.admin.util.JsonResponseMsg;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("bank")
public class BankController {
    @Resource
    private BankService bankService;
    @Resource
    private BankLinkMapper bankLinkMapper;
    @Resource
    private CooperativeLinkProductMapper cooperativeLinkProductMapper;


    @RequestMapping(value = "bankList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg bankList(@RequestParam(defaultValue = "10", required = false) Integer limit,
                                    @RequestParam(defaultValue = "1", required = false) Integer offset, String name){
        JsonResponseMsg result = new JsonResponseMsg();
        Page<BankDto> page = new Page<>(limit, offset);
        bankService.bankList(page,name);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }


    /**
     * 修改银行排名
     */
    @RequestMapping(value = "updateSort",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateSort(@RequestBody SortDto bankSortDto){
        JsonResponseMsg result = new JsonResponseMsg();
        if(bankSortDto.getNextId()==null||bankSortDto.getTopId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"参数错误");
        }

        Bank bankTop = bankService.queryByBankId(bankSortDto.getTopId());
        if(bankTop==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"修改的银行不存在");
        }
        Bank bankNext = bankService.queryByBankId(bankSortDto.getNextId());
        if(bankNext==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"修改的银行不存在");
        }
        Integer topSort = bankTop.getSort();
        Integer nextSort = bankNext.getSort();
        bankTop.setSort(nextSort);
        bankNext.setSort(topSort);
        bankService.update(bankTop);
        bankService.update(bankNext);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改排名成功");
    }

    /**
     * 修改银行信息
     */
    @RequestMapping(value = "updateBankInfo",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateBankInfo(@RequestBody Bank bank){
        JsonResponseMsg result = new JsonResponseMsg();

        if(bank.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行的Id");
        }
        if(StringUtils.isEmpty(bank.getIamge())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行图片");
        }

        if(StringUtils.isEmpty(bank.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行名称");
        }
        Bank bank1 = bankService.queryByBankId(bank.getId());
        if(!bank1.getName().equals(bank.getName())){
            Bank bank2 = bankService.queryByBankName(bank.getName());
            if(bank2!=null){
                return result.fill(JsonResponseMsg.CODE_FAIL,"该银行名称已经存在");
            }
        }
        if(StringUtils.isEmpty(bank.getIntroduce())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入银行的介绍");
        }
        bank.setUpdatedTime(new Date());
        bankService.update(bank);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改银行号信息成功");
    }
    /**
     * 银行添加
     * @param bank
     * @return
     */
    @RequestMapping(value = "addBankInfo",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addBankInfo(@RequestBody Bank bank){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(bank.getIamge())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行图片");
        }

        if(StringUtils.isEmpty(bank.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行名称");
        }
        Bank bank2 = bankService.queryByBankName(bank.getName());
        if(bank2!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"该银行名称已经存在");
        }

        if(StringUtils.isEmpty(bank.getIntroduce())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入银行的介绍");
        }
        bank.setCreateTime(new Date());
        bank.setIsEmploy(CommonConstant.IS_EMPLOY);
        Integer sort = bankService.queryMaxSort();
        bank.setSort(sort+1);
        bankService.insert(bank);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加银行成功");
    }


    /**
     * 银行信息删除
     */

    @RequestMapping(value = "deleteBankById",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteBankById(String bankId){
        JsonResponseMsg result = new JsonResponseMsg();

        if(!NumberUtils.isNumber(bankId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行Id");
        }
        Bank bank = bankService.queryByBankId(NumberUtils.toLong(bankId));
        if(bank==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你删除的银行信息不存在");
        }

        List<BankLink> bankLink = bankLinkMapper.queryByBankId(NumberUtils.toLong(bankId));
        if(bankLink.size()!=0){
            return result.fill(JsonResponseMsg.CODE_FAIL,"改银行还有链接存着,不能进行删除");
        }
        bank.setIsEmploy(CommonConstant.NO_EMPLOY);
        bankService.update(bank);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除成功");
    }

    /**
     * 银行链接列表
     */

    @RequestMapping(value = "bankLinkList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg bankLinkList(@RequestParam(defaultValue = "10", required = false) Integer limit,
                                    @RequestParam(defaultValue = "1", required = false) Integer offset, String bankLinkId){
        JsonResponseMsg result = new JsonResponseMsg();

        if(!NumberUtils.isNumber(bankLinkId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入银行Id");
        }
        Page<BankLinkDto> page = new Page<>(limit, offset);
        bankService.bankLinkList(page,NumberUtils.toLong(bankLinkId));
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }

    /**
     * 添加银行链接
     */
    @RequestMapping(value = "addBankLink",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addBankLink(@RequestBody BankLink bankLink){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(bankLink.getUrl())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Url");
        }
        if(bankLink.getBankId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行Id");
        }
        bankLink.setIsEmploy(CommonConstant.IS_EMPLOY);
        bankLink.setCreateTime(new Date());
        bankLink.setUpdatedTime(new Date());
        bankLinkMapper.insertSelective(bankLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加链接成功");
    }

    /**
     * 修改银行链接
     */
    @RequestMapping(value = "updateBankLink",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateBankLink(@RequestBody BankLink bankLink){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(bankLink.getUrl())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Url");
        }
        if(bankLink.getBankId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行Id");
        }
        if(bankLink.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Id");
        }
        bankLink.setUpdatedTime(new Date());
        bankLinkMapper.updateByPrimaryKeySelective(bankLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改链接成功");
    }
    /**
     * 删除银行链接
     */
    @RequestMapping(value = "deleteBankLinkById",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteBankLinkById(String bankLinkId){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(bankLinkId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Id");
        }
        BankLink bankLink = bankLinkMapper.selectByPrimaryKey(NumberUtils.toLong(bankLinkId));
        if(bankLink==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要上传银行链接不存在");
        }
        CooperativeLinkProduct cooperativeLinkProduct =cooperativeLinkProductMapper.queryByBankLinkId(NumberUtils.toLong(bankLinkId));
        if(cooperativeLinkProduct!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"链接有客户正在使用，不能进行删除");
        }
        bankLink.setIsEmploy(CommonConstant.NO_EMPLOY);
        bankLinkMapper.updateByPrimaryKeySelective(bankLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除链接成功");
    }

}
