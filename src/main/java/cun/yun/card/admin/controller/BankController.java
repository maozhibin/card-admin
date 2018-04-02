package cun.yun.card.admin.controller;

import cun.yun.card.admin.dal.dto.BankDto;
import cun.yun.card.admin.dal.dto.BankSortDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bank;
import cun.yun.card.admin.dal.model.Role;
import cun.yun.card.admin.dal.service.BankService;
import cun.yun.card.admin.util.JsonResponseMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("bank")
public class BankController {
    @Resource
    private BankService bankService;

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
    public JsonResponseMsg updateSort(@RequestBody BankSortDto bankSortDto){
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




}
