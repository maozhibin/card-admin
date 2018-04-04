package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BankLinkDto {

    private Long id;

    private Long bankId;

    private String bankName;

    private String url;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createTime;

}
