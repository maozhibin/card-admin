package cun.yun.card.admin.controller;

import cun.yun.card.admin.common.Util;
import cun.yun.card.admin.dal.dto.AdminDto;
import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Admin;
import cun.yun.card.admin.dal.model.Menu;
import cun.yun.card.admin.dal.service.AdminService;
import cun.yun.card.admin.dal.service.MenuService;
import cun.yun.card.admin.dal.service.RedisService;
import cun.yun.card.admin.util.JsonResponseMsg;
import cun.yun.card.admin.util.Md5;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Resource
    private RedisService redisService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request,@RequestBody AdminDto adminDto) {
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

        //登录状态存储到redis中
        Object token = admin.getId()+new Date().getTime();
        redisService.set(String.valueOf(admin.getId()),token,3000L);

//        request.getSession(true).setAttribute("ADMIN_USER_ID", admin.getId());
//        request.getSession(true).setMaxInactiveInterval(1800);

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
//        map.put("token",token);
        map.put("adminId",admin.getId());
        map.put("adminName",admin.getName());
        Admin admin1 = new Admin();
        admin1.setId(admin.getId());
        admin1.setLastLoginTime(new Date());
        adminService.update(admin1);

        return result.fill(JsonResponseMsg.CODE_SUCCESS,"登录成功",map);
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public Object logout(String adminId) {
        JsonResponseMsg result = new JsonResponseMsg();
//        request.getSession(true).removeAttribute("ADMIN_USER_ID");
        redisService.remove(adminId);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"退出登录成功");
    }


    /**
     * 管理员列表
     */
    @RequestMapping(value = "adminList",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg roleList(@RequestParam(defaultValue = "10", required = false) Integer limit,
                                    @RequestParam(defaultValue = "1", required = false) Integer offset, String name){
        JsonResponseMsg result = new JsonResponseMsg();
        Page<AdminDto> page = new Page<>(limit, offset);
        adminService.adminList(page,name);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查询成功",map);
    }

    /**
     * 管理员添加
     */
    @RequestMapping(value = "addAdmin",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg addMenu(@RequestBody AdminDto adminDto) {
        JsonResponseMsg result = new JsonResponseMsg();
        Admin admin = new Admin();
        if(StringUtils.isEmpty(adminDto.getUserName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入用户名");
        }
        Admin adminOld = adminService.queryByUserName(adminDto.getUserName());
        if(adminOld!=null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"该管理员名称已经被占用，请重新输入");
        }
        admin.setName(adminDto.getUserName());
        if(!Util.isEmail(adminDto.getMailbox())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入正确的邮箱");
        }
        admin.setMailbox(adminDto.getMailbox());
        if(!Util.isMobile(adminDto.getMobile())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入正确的手机号");
        }
        admin.setMobile(adminDto.getMobile());

        if(adminDto.getRoleId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请选择角色");
        }
        admin.setRoleId(adminDto.getRoleId());
        if(StringUtils.isEmpty(adminDto.getPassword())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入密码");
        }
        admin.setPassword(adminDto.getPassword());
        admin.setCreatedTime(new Date());
        admin.setIsEmploy(0);
        admin.setUpdatedTime(new Date());
        adminService.insert(admin);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加成功");
    }


    /**
     * 管理员修改
     */
    @RequestMapping(value = "updateAdmin",method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public JsonResponseMsg updateAdmin(@RequestBody AdminDto adminDto) {
        JsonResponseMsg result = new JsonResponseMsg();
        if(adminDto.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"参数错误");
        }
        Admin admin = adminService.queryById(adminDto.getId());
        if(admin==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"改用户不存在");
        }
        if(StringUtils.isEmpty(adminDto.getUserName())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入用户名");
        }
        if(!adminDto.getUserName().equals(admin.getName())){
            Admin adminOld = adminService.queryByUserName(adminDto.getUserName());
            if(adminOld!=null){
                return result.fill(JsonResponseMsg.CODE_FAIL,"该管理员名称已经被占用，请重新输入");
            }
            admin.setName(adminDto.getUserName());
        }

        if(!Util.isEmail(adminDto.getMailbox())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入正确的邮箱");
        }
        admin.setMailbox(adminDto.getMailbox());
        if(!Util.isMobile(adminDto.getMobile())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入正确的手机号");
        }
        admin.setMobile(adminDto.getMobile());

        if(adminDto.getRoleId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请选择角色");
        }
        admin.setRoleId(adminDto.getRoleId());
        if(StringUtils.isEmpty(adminDto.getPassword())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入密码");
        }
        admin.setPassword(adminDto.getPassword());
        admin.setIsEmploy(0);
        admin.setUpdatedTime(new Date());
        adminService.update(admin);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改成");
    }
}
