package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApplayDto {
    private Long adminId;

    private List<Long> linkId;

}
