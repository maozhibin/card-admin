package cun.yun.card.admin.controller;
import cun.yun.card.admin.common.CommonConstant;
import cun.yun.card.admin.dal.dao.CooperativePartnerLinkMapper;
import cun.yun.card.admin.dal.dto.ApplayDto;
import cun.yun.card.admin.dal.model.CooperativeLinkProduct;
import cun.yun.card.admin.dal.model.CooperativePartner;
import cun.yun.card.admin.dal.model.CooperativePartnerLink;
import cun.yun.card.admin.dal.service.CooperativeLinkProductService;
import cun.yun.card.admin.dal.service.CooperativePartnerService;
import cun.yun.card.admin.util.JsonResponseMsg;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("cooperativeLinkProduct")
public class CooperativeLinkProductController {
    @Resource
    private CooperativeLinkProductService cooperativeLinkProductService;
    @Resource
    private CooperativePartnerService cooperativePartnerService;
    @Resource
    private CooperativePartnerLinkMapper cooperativePartnerLinkMapper;
    /**
     * 信用卡链接申请
     * @param
     * @return
     */
    @RequestMapping(value = "/cardApplay",method = RequestMethod.POST)
    @ResponseBody
    public Object cardApplay(@RequestBody ApplayDto applayDto) {
        JsonResponseMsg result = new JsonResponseMsg();
        if(applayDto.getAdminId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入adminId");
        }
        if(applayDto.getLinkId().size()==0){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请选择要申请的链接");
        }
//        if(applayDto.getType()!=1){
//            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入正确的类型");
//        }
        CooperativePartner cooperativePartner = cooperativePartnerService.queryByAdminId(applayDto.getAdminId());
        if(cooperativePartner==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"没有该合作商");
        }
        CooperativePartnerLink cooperativePartnerLink = new CooperativePartnerLink();
        cooperativePartnerLink.setCooperativePartnerId(cooperativePartner.getId());
        cooperativePartnerLink.setCreatedTime(new Date());
        cooperativePartnerLink.setUpdatedTime(new Date());
        cooperativePartnerLink.setIsEmploy(CommonConstant.IS_EMPLOY);
        cooperativePartnerLink.setProductType(1);
        Map<String,Object> map = new HashMap<>();
        //如果个数为1的话是单链接，2的话为综合链接
        if(applayDto.getLinkId().size()==1){
            map.put("type",1);
            cooperativePartnerLink.setLinkType(1);
        }else {
            map.put("type",2);
            map.put("type",2);
            cooperativePartnerLink.setLinkType(2);
        }
        List<Long> lists =applayDto.getLinkId();
        List<CooperativeLinkProduct> cooperativeLinkProducts = new ArrayList<>();
        for (Long list:lists) {
            cooperativePartnerLinkMapper.insertLink(cooperativePartnerLink);
            CooperativeLinkProduct cooperativeLinkProduct = new CooperativeLinkProduct();
            cooperativeLinkProduct.setCooperativePartnerLinkId(cooperativePartnerLink.getId());
            cooperativeLinkProduct.setType(String.valueOf(1));
            cooperativeLinkProduct.setLinkId(list);
            cooperativeLinkProduct.setIsEmploy(CommonConstant.IS_EMPLOY);
            cooperativeLinkProduct.setCreateTime(new Date());
            cooperativeLinkProduct.setUpdatedTime(new Date());
            cooperativeLinkProducts.add(cooperativeLinkProduct);
        }
        cooperativeLinkProductService.insertList(cooperativeLinkProducts);
        map.put("id",cooperativePartnerLink.getId());
        map.put("cooperativePartnerCode",cooperativePartner.getCode());
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"申请链接成功");
    }

}
