package cun.yun.card.admin.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class CooperativePartnerLink {
    private Long id;

    private Long cooperativePartnerId;

    private String url;

    private Integer linkType;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createdTime;

    private Integer productType;
}