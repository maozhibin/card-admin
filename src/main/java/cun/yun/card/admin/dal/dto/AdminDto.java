package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AdminDto {

    private Long id;

    private String mailbox;

    private String mobile;

    private Long roleId;

    private String roleName;

    private Date lastLoginTime;

    private String userName;

    private String password;
}
