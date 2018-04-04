package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class BondDto {
    private String bondName;

    private Long id;

    private String iamge;

    private String introduce;

    private Integer count;

    private Integer sort;

    private BigDecimal price;
}
