package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.CooperativeLinkProductMapper;
import cun.yun.card.admin.dal.model.CooperativeLinkProduct;
import cun.yun.card.admin.dal.service.CooperativeLinkProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CooperativeLinkProductServiceImpl implements CooperativeLinkProductService {
    @Resource
    private CooperativeLinkProductMapper cooperativeLinkProductMapper;

    @Override
    public void insertList(List<CooperativeLinkProduct> cooperativeLinkProducts) {
        cooperativeLinkProductMapper.insertList(cooperativeLinkProducts);
    }
}
