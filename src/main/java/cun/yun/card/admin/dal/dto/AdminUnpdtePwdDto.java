package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminUnpdtePwdDto {

    private String oldPwd;

    private String newPwd;

    private Long Id;
}
