package cun.yun.card.admin.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class LoanLink {
    private Long id;

    private Long loanId;

    private String url;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createdTime;

}