package cun.yun.card.admin.dal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MenuDto {
    private Long id;

    private String url;

    private String name;

    private Integer isMenu;

    private Long parentId;

    List<MenuDto> menulist = new ArrayList<>();
}
