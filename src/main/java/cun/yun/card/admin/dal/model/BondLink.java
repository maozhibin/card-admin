package cun.yun.card.admin.dal.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class BondLink {
    private Long id;

    private Long bondId;

    private String url;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createTime;

}