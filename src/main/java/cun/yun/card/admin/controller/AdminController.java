package cun.yun.card.admin.controller;

import cun.yun.card.admin.common.CommonConstant;
import cun.yun.card.admin.common.Util;
import cun.yun.card.admin.dal.dao.RoleAdminMapper;
import cun.yun.card.admin.dal.dto.AdminDto;
import cun.yun.card.admin.dal.dto.AdminUnpdtePwdDto;
import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.ext.Page;
import cun.yun.card.admin.dal.model.Admin;
import cun.yun.card.admin.dal.model.Menu;
import cun.yun.card.admin.dal.model.RoleAdmin;
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
    @Resource
    private RoleAdminMapper roleAdminMapper;


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


        if(StringUtils.isEmpty(adminDto.getPassword())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入密码");
        }
        admin.setPassword(adminDto.getPassword());
        admin.setCreatedTime(new Date());
        admin.setIsEmploy(0);
        admin.setUpdatedTime(new Date());
        adminService.insert(admin);

        admin = adminService.queryByUserName(admin.getName());
        RoleAdmin roleAdmin = new RoleAdmin();
        roleAdmin.setAdminId(admin.getId());
        roleAdmin.setRoleId(adminDto.getRoleId());
        roleAdminMapper.insertSelective(roleAdmin);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"添加成功");
    }


    /**
     * 管理员修改
     */
    @RequestMapping(value = "updateAdmin",method = RequestMethod.POST)
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
        RoleAdmin roleAdmin = new RoleAdmin();
        roleAdmin.setAdminId(adminDto.getId());
        roleAdmin.setRoleId(adminDto.getRoleId());
        admin.setPassword(adminDto.getPassword());
        admin.setIsEmploy(0);
        admin.setUpdatedTime(new Date());
        adminService.update(admin);
        roleAdminMapper.insertSelective(roleAdmin);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"修改成");
    }


    /**
     * 管理员删除
     */
    @RequestMapping(value = "deleteAdmin",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseMsg deleteAdmin(String Id) {
        JsonResponseMsg result = new JsonResponseMsg();
        if(StringUtils.isEmpty(Id)){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入管理员Id");
        }

        Admin admin = adminService.queryById(NumberUtils.toLong(Id));

        if(admin==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你删除的管理员不存在");
        }
        admin.setIsEmploy(CommonConstant.NO_EMPLOY);
        adminService.update(admin);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除成功");
    }

    /**
     * 管理员密码修改
     */
    @RequestMapping(value = "updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseMsg updatePwd(@RequestBody AdminUnpdtePwdDto adminUnpdtePwdDto) {
        JsonResponseMsg result = new JsonResponseMsg();

        if(adminUnpdtePwdDto.getId()==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要操作的管理员不存在");
        }

        if(StringUtils.isEmpty(adminUnpdtePwdDto.getOldPwd())||StringUtils.isEmpty(adminUnpdtePwdDto.getNewPwd())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"请输入你的密码");
        }

        Admin admin = adminService.queryById(adminUnpdtePwdDto.getId());
        if(admin==null){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你要操作的管理员不存在");
        }
        if(adminUnpdtePwdDto.getOldPwd().equals(admin.getPassword())){
            return result.fill(JsonResponseMsg.CODE_FAIL,"你输入的原先密码不正确");
        }
        admin.setPassword(adminUnpdtePwdDto.getNewPwd());
        adminService.insert(admin);


//        if(StringUtils.isEmpty(adminId)){
//            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入管理员Id");
//        }
//
//        Admin admin = adminService.queryById(NumberUtils.toLong(adminId));
//
//        if(admin==null){
//            return result.fill(JsonResponseMsg.CODE_FAIL,"你删除的管理员不存在");
//        }
//        admin.setIsEmploy(CommonConstant.NO_EMPLOY);
//        adminService.update(admin);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"删除成功");
    }



}
