package cun.yun.card.admin.controller;

import cun.yun.card.admin.common.CommonConstant;
import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.model.Menu;
import cun.yun.card.admin.dal.service.MenuService;
import cun.yun.card.admin.dal.service.RoleMenuService;
import cun.yun.card.admin.util.JsonResponseMsg;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuService roleMenuService;
    /**
     * 菜单列表
     * @return
     */
    @RequestMapping(value = "menuList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg menuList(){
        JsonResponseMsg result = new JsonResponseMsg();

        List<MenuDto> rootMenu  = menuService.queryAllInfo();

        // 最后的结果
        List<MenuDto> menuList = new ArrayList<>();
        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单parentId=0
            if (rootMenu.get(i).getParentId()==0) {
                menuList.add(rootMenu.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (MenuDto menu : menuList) {
            menu.setMenulist(menuService.getChild(menu.getId(), rootMenu));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("menus",menuList);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查询成功",map);
    }


    /**
     * 添加菜单
     */
    @RequestMapping(value = "addMenu",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addMenu(@RequestBody Menu menu){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(menu.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入菜单名");
        }
        if(StringUtils.isEmpty(menu.getUrl())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入地址");
        }
        if(menu.getParentId()==null){//若果父及菜单为null则设置为0本身就是父及菜单
            menu.setParentId(0L);
        }
        if(menu.getIsMenu()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请选择菜单属性");
        }
        menu.setIsEmploy(CommonConstant.IS_EMPLOY);
        Date date = new Date();
        menu.setCreatedTime(date);
        menu.setUpdatedTime(date);
        menuService.addMenu(menu);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加成功");
    }


    /**
     * 修改菜单
     */
    @RequestMapping(value = "updateMenu",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateMenu(@RequestBody Menu menu){
        JsonResponseMsg result = new JsonResponseMsg();
        if(menu.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入菜单Id");
        }
        if(StringUtils.isEmpty(menu.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入菜单名");
        }
//        if(StringUtils.isEmpty(menu.getUrl())){
//            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入地址");
//        }
        if(menu.getParentId()==null){//若果父及菜单为null则设置为0本身就是父及菜单
            menu.setParentId(0L);
        }
//        if(menu.getIsMenu()==null){
//            return result.fill(JsonResponseMsg.CODE_FAIL,"请选择菜单属性");
//        }
        menu.setIsEmploy(CommonConstant.IS_EMPLOY);
        Date date = new Date();
        menu.setUpdatedTime(date);
        menuService.updateMenu(menu);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改成功");
    }

    /**
     * 删除菜单
     */
    @RequestMapping(value = "deleteMenu",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteMenu(String id){
        JsonResponseMsg result = new JsonResponseMsg();
        if(!NumberUtils.isNumber(id)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入菜单Id");
        }
        menuService.deleteMenu(id);
        roleMenuService.deleteByMenuId(NumberUtils.toLong(id));
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除成功");
    }

}
