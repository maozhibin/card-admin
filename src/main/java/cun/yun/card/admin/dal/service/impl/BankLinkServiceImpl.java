package cun.yun.card.admin.dal.service.impl;

import cun.yun.card.admin.dal.dao.BankLinkMapper;
import cun.yun.card.admin.dal.service.BankLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BankLinkServiceImpl implements BankLinkService {
    @Resource
    private BankLinkMapper bankLinkMapper;
}
