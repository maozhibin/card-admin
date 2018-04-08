package cun.yun.card.admin.controller;

import cun.yun.card.admin.common.CommonConstant;
import cun.yun.card.admin.dal.dao.BondLinkMapper;
import cun.yun.card.admin.dal.dao.CooperatePartnerStatisticsDayMapper;
import cun.yun.card.admin.dal.dao.CooperativeLinkProductMapper;
import cun.yun.card.admin.dal.dto.BondDto;
import cun.yun.card.admin.dal.dto.BondLinkDto;
import cun.yun.card.admin.dal.dto.SortDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Bond;
import cun.yun.card.admin.dal.model.BondLink;
import cun.yun.card.admin.dal.model.CooperativeLinkProduct;
import cun.yun.card.admin.dal.service.BondLinkService;
import cun.yun.card.admin.dal.service.BondService;
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
@RequestMapping("bond")
public class BondController {
    @Resource
    private BondService bondService;
    @Resource
    private BondLinkService bondLinkService;
    @Resource
    private CooperativeLinkProductMapper cooperativeLinkProductMapper;

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


    /**
     * 证券添加
     */
    @RequestMapping(value = "addBond",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addBond(@RequestBody Bond bond){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(bond.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入证券名称");
        }
        if(StringUtils.isEmpty(bond.getIamge())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"传入证券图片");
        }
        if(StringUtils.isEmpty(bond.getIntroduce())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入证券介绍");
        }

        Bond bond1 = bondService.queryByBondName(bond.getName());
        if(bond1!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你输入的证券名称已经存在，请重新输入");
        }
        bond.setIsEmploy(CommonConstant.IS_EMPLOY);
        bond.setUpdatedTime(new Date());
        bond.setCreateTime(new Date());
        Integer maxSort = bondService.queryMaxSort();
        bond.setSort(maxSort+1);
        bondService.insert(bond);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加证券成功");
    }

    /**
     * 证券修改
     */
    @RequestMapping(value = "updateBond",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateBond(@RequestBody Bond bond){
        JsonResponseMsg result = new JsonResponseMsg();
        if(bond.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入Id");
        }
        if(StringUtils.isEmpty(bond.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入证券名称");
        }
        if(StringUtils.isEmpty(bond.getIamge())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"传入证券图片");
        }
        if(StringUtils.isEmpty(bond.getIntroduce())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入证券介绍");
        }
        Bond bond1 = bondService.queryByBankId(bond.getId());
        if(bond1==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要修改的证券不存在");
        }else{
            if(!bond1.getName().equals(bond.getName())){
                Bond bond2 = bondService.queryByBondName(bond.getName());
                if(bond2!=null){
                    return result.fill(JsonResponseMsg.CODE_FAIL,"你输入的证券名称已经存在，请重新输入");
                }
            }
        }
        bond.setUpdatedTime(new Date());
        bondService.update(bond);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改证券成功");
    }

    /**
     * 证券删除
     */
    @RequestMapping(value = "deleteBond",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteBond(String bondId){
        JsonResponseMsg result = new JsonResponseMsg();
        if(!NumberUtils.isNumber(bondId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入需要删除的证券Id");
        }
        List<BondLink> list = bondLinkService.queryByBondId(NumberUtils.toLong(bondId));
        if(list.size()!=0){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要删除的证券还有链接在使用，暂时不能进行删除");
        }
        Bond bond = bondService.selectByPrimaryKey(NumberUtils.toLong(bondId));
        if(bond==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要删除的证券不存在");
        }
        bond.setIsEmploy(CommonConstant.NO_EMPLOY);
        bondService.update(bond);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除成功");
    }

    /**
     * 证券链接列表
     */
    @RequestMapping(value = "bondLinkList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg bondLinkList(@RequestParam(defaultValue = "10", required = false) Integer limit,
                                        @RequestParam(defaultValue = "1", required = false) Integer offset, String bondId){
        JsonResponseMsg result = new JsonResponseMsg();

        if(!NumberUtils.isNumber(bondId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入证券Id");
        }
        Page<BondLinkDto> page = new Page<>(limit, offset);
        bondService.bondLinkList(page,NumberUtils.toLong(bondId));
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }

    /**
     * 添加证券链接
     */
    @RequestMapping(value = "addBondLink",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addBondLink(@RequestBody BondLink bondLink){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(bondLink.getUrl())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Url");
        }
        if(bondLink.getBondId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行Id");
        }
        bondLink.setIsEmploy(CommonConstant.IS_EMPLOY);
        bondLink.setCreateTime(new Date());
        bondLink.setUpdatedTime(new Date());
        bondLinkService.insertSelective(bondLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加链接成功");
    }

    /**
     * 修改证券链接
     */
    @RequestMapping(value = "updateBondLink",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateBondLink(@RequestBody BondLink bondLink){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(bondLink.getUrl())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Url");
        }
        if(bondLink.getBondId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入银行Id");
        }
        if(bondLink.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Id");
        }
        bondLink.setUpdatedTime(new Date());
        bondLinkService.updateByPrimaryKeySelective(bondLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改链接成功");
    }
    /**
     * 删除证券链接
     */
    @RequestMapping(value = "deleteBondLinkById",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteBondLinkById(String bondLinkId){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(bondLinkId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入链接Id");
        }
        BondLink bondLink = bondLinkService.selectByPrimaryKey(NumberUtils.toLong(bondLinkId));
        if(bondLink==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要删除的证券链接不存在");
        }
        CooperativeLinkProduct cooperativeLinkProduct =cooperativeLinkProductMapper.queryByBondLinkId(NumberUtils.toLong(bondLinkId));
        if(cooperativeLinkProduct!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"链接有客户正在使用，不能进行删除");
        }
        bondLink.setIsEmploy(CommonConstant.NO_EMPLOY);
        bondLinkService.updateByPrimaryKeySelective(bondLink);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除链接成功");
    }

}
