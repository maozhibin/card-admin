package cun.yun.card.admin.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class Loan {
    private Long id;

    private Integer sort;

    private String name;

    private BigDecimal price;

    private BigDecimal limitMin;

    private BigDecimal limitMax;

    private Double moneyRate;

    private String image;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createTime;

}