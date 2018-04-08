package cun.yun.card.admin.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class CooperativeLinkProduct {
    private Long id;

    private Long cooperativePartnerLinkId;

    private String type;

    private Long linkId;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createTime;
}