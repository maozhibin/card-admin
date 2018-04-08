package cun.yun.card.admin.dal.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class Admin {
    private Long id;

    private String name;

    private String mailbox;

    private String mobile;

    private String password;

    private Integer isEmploy;

    private Date createdTime;

    private Date updatedTime;

    private Date lastLoginTime;

}