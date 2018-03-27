package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class RoleDto {

    private Long id;

    private String name;

    private Integer isEmploy;

    private Date updatedTime;

    private Date createTime;

    private List<Long> menuIds;
}
