package cun.yun.card.admin.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class BankLink {
    private Long id;

    private Long bankId;

    private String url;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createTime;



}