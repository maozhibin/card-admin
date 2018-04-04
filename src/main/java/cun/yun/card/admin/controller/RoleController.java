package cun.yun.card.admin.controller;

import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.dto.RoleDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Role;
import cun.yun.card.admin.dal.model.RoleAdmin;
import cun.yun.card.admin.dal.model.RoleMenu;
import cun.yun.card.admin.dal.service.MenuService;
import cun.yun.card.admin.dal.service.RoleAdminService;
import cun.yun.card.admin.dal.service.RoleMenuService;
import cun.yun.card.admin.dal.service.RoleService;
import cun.yun.card.admin.util.JsonResponseMsg;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private RoleAdminService roleAdminService;

    /**
     * 角色列表
     */
    @RequestMapping(value = "roleList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg roleList(@RequestParam(defaultValue = "10", required = false) Integer limit,
                                     @RequestParam(defaultValue = "1", required = false) Integer offset, String name){
        JsonResponseMsg result = new JsonResponseMsg();

        Page<Role> page = new Page<>(limit, offset);
        roleService.roleList(page,name);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }


    /**
     * 所有角色
     */
    @RequestMapping(value = "roleAll",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg roleAll(){
        JsonResponseMsg result = new JsonResponseMsg();
        Map<String,Object> map = new HashMap<>();
        List<Role> list = roleService.queryALL();
        map.put("list",list);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }


    /**
     * 角色权限查询
     */
    @RequestMapping(value = "rolePower",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg rolePower(String id){
        JsonResponseMsg result = new JsonResponseMsg();
        if(!NumberUtils.isNumber(id)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入有效的角色id");
        }
        List<MenuDto> rootMenu = roleService.queryMenuByRoleId(NumberUtils.toLong(id));
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
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查出成功",map);
    }

    /**
     * 角色添加
     */
    @RequestMapping(value = "addRole",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addRole(@RequestBody RoleDto roleDto){
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(roleDto.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入角色名称");
        }
        if(roleDto.getMenuIds().size()==0){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请选择至少一项权限");
        }
        Role rolenName = roleService.queryByRoleName(roleDto.getName());
        if(rolenName!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你输入的角色名称已经存在了，请重新输入名称");
        }
        //添加角色
        Role role = new Role();
        Date date = new Date();
        role.setIsEmploy(0);
        role.setCreateTime(date);
        role.setUpdatedTime(date);
        role.setName(roleDto.getName());
        roleService.insert(role);
        role = roleService.queryByRoleName(roleDto.getName());
        //添加角色拥有的权限
        List<Long> lists = roleDto.getMenuIds();
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Long list:lists){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(list);
            roleMenu.setRoleId(role.getId());
            roleMenus.add(roleMenu);
        }
        roleMenuService.insertList(roleMenus);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加成功");
    }
    /**
     * 角色修改
     */
    @RequestMapping(value = "updateRole",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updateRole(@RequestBody RoleDto roleDto){
        JsonResponseMsg result = new JsonResponseMsg();
        if(roleDto.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入角色ID");
        }
        if(StringUtils.isEmpty(roleDto.getName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入角色名称");
        }
        if(roleDto.getMenuIds().size()==0){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请选择至少一项权限");
        }
        Role roleByID = roleService.queryById(roleDto.getId());
        if(roleByID==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要修改的角色不存在");
        }
        if(!roleByID.getName().equals(roleDto.getName())){//如果角色名和原来相同就不判断角色名是否存在
            Role rolenName = roleService.queryByRoleName(roleDto.getName());
            if(rolenName!=null){
                return result.fill(JsonResponseMsg.CODE_FAIL,"你  输入的角色名称已经存在了，请重新输入名称");
            }
        }
        //修改角色
        Role role = new Role();
        Date date = new Date();
        role.setUpdatedTime(date);
        role.setName(roleDto.getName());
        roleService.update(role);
        //删除原来的角色权限
        roleMenuService.deleteByRoleId(roleDto.getId());
        //添加角色拥有的权限
        List<Long> lists = roleDto.getMenuIds();
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Long list:lists){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(list);
            roleMenu.setRoleId(role.getId());
            roleMenus.add(roleMenu);
        }
        roleMenuService.insertList(roleMenus);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"");
    }

    /**
     * 角色删除
     */

    @RequestMapping(value = "deleteRole",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteRole(String roleId){
        JsonResponseMsg result = new JsonResponseMsg();
        if(!NumberUtils.isNumber(roleId)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入角色id");
        }
        RoleAdmin roleAdmin = roleAdminService.queryByRoleId(NumberUtils.toLong(roleId));
        if(roleAdmin!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"还有用户使用该角色暂时不能进行删除");
        }
        roleService.deleteById(roleId);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除角色成功");
    }






}
