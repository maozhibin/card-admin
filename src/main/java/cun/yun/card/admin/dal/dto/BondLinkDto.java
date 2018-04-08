package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BondLinkDto {
    private Long id;

    private Long BondId;

    private String bondName;

    private String url;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createTime;
}
