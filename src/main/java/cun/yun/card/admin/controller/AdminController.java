package cun.yun.card.admin.controller;

import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.model.Admin;
import cun.yun.card.admin.dal.service.AdminService;
import cun.yun.card.admin.dal.service.MenuService;
import cun.yun.card.admin.util.JsonResponseMsg;
import cun.yun.card.admin.util.Md5;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping({"/login"})
    @ResponseBody
    public Object login(String userName, String password, HttpServletRequest request) {
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            return result.fill(JsonResponseMsg.CODE_WRONG_PARAM,JsonResponseMsg.MSG_WRONG_PARAM);
        }
        String pwd = Md5.encrypt32(password);
        Admin admin = adminService.queryByUserPass(userName, pwd);
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
            menu.setMenulist(getChild(menu.getId(), rootMenu));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("menus",menuList);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"登录成功",map);
    }

    /**
     * 递归查找子菜单
     * 当前菜单id
     * 要查找的列表
     * @return
     */
    private List<MenuDto> getChild(Long id, List<MenuDto> rootMenu) {
        // 子菜单
        List<MenuDto> childList = new ArrayList<>();
        for (MenuDto menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId()!=null) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (MenuDto menu : childList) {
                // 递归
                menu.setMenulist(getChild(menu.getId(), rootMenu));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
