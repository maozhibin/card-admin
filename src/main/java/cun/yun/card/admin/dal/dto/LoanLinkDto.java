package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class LoanLinkDto {
    private Long id;

    private Long loanId;

    private String loanName;

    private String url;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createTime;
}
