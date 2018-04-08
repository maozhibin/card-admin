package cun.yun.card.admin.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class Bond {
    private Long id;

    private String name;

    private String iamge;

    private BigDecimal price;

    private Integer sort;

    private Integer isEmploy;

    private Date updatedTime;

    private String introduce;

    private Date createTime;
}