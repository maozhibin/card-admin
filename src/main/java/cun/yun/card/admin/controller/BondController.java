package cun.yun.card.admin.controller;

import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.dto.SortDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bond;
import cun.yun.card.admin.dal.service.BondService;
import cun.yun.card.admin.util.JsonResponseMsg;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("bond")
public class BondController {
    @Resource
    private BondService bondService;


    @RequestMapping(value = "bondList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg bondList(@RequestParam(defaultValue = "10", required = false) Integer limit,
                                    @RequestParam(defaultValue = "1", required = false) Integer offset, String name){
        JsonResponseMsg result = new JsonResponseMsg();
        Page<BondDto> page = new Page<>(limit, offset);
        bondService.bondList(page,name);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }



    /**
     * 修改证券排名
     */
    @RequestMapping(value = "updateSort",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateSort(@RequestBody SortDto sortDto){
        JsonResponseMsg result = new JsonResponseMsg();
        if(sortDto.getNextId()==null||sortDto.getTopId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"参数错误");
        }

        Bond bondTop = bondService.queryByBankId(sortDto.getTopId());
        if(bondTop==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"修改的证券不存在");
        }
        Bond bondNext = bondService.queryByBankId(sortDto.getNextId());
        if(bondNext==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"修改的证券不存在");
        }
        Integer topSort = bondTop.getSort();
        Integer nextSort = bondNext.getSort();
        bondTop.setSort(nextSort);
        bondNext.setSort(topSort);
        bondService.update(bondTop);
        bondService.update(bondNext);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改排名成功");
    }



}
