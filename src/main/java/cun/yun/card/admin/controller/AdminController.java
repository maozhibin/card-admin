package cun.yun.card.admin.controller;

import cun.yun.card.admin.dal.dto.AdminDto;
import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.model.Admin;
import cun.yun.card.admin.dal.service.AdminService;
import cun.yun.card.admin.dal.service.MenuService;
import cun.yun.card.admin.util.JsonResponseMsg;
import cun.yun.card.admin.util.Md5;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;
    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login( HttpServletRequest request ,@RequestBody AdminDto adminDto) {
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(adminDto.getUserName()) || StringUtils.isEmpty(adminDto.getPassword())){
            return result.fill(JsonResponseMsg.CODE_WRONG_PARAM,JsonResponseMsg.MSG_WRONG_PARAM);
        }
//        String pwd = Md5.encrypt32(password);
        Admin admin = adminService.queryByUserPass(adminDto.getUserName(), adminDto.getPassword());
        if (admin==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"账号或者密码错误");
        }
        admin.setLastLoginTime(new Date());
        request.getSession(true).setAttribute("ADMIN_USER_ID", admin.getId());
        request.getSession(true).setMaxInactiveInterval(1800);
        List<MenuDto> rootMenu  = menuService.queryByAdminIdAndParentId(admin.getId());
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
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"登录成功",map);
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public Object logout( HttpServletRequest request) {
        JsonResponseMsg result = new JsonResponseMsg();
        request.getSession(true).removeAttribute("ADMIN_USER_ID");
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"退出登录成功");
    }



}
