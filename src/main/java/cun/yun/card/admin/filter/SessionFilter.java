package cun.yun.card.admin.filter;

import com.alibaba.fastjson.JSONObject;
import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.service.MenuService;
import cun.yun.card.admin.dal.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@WebFilter(filterName="SessionFilter")
public class SessionFilter  implements Filter {
    @Resource
    private MenuService menuService;
    @Resource
    private RedisService redisService;
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String method =  req.getMethod();
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, x-requested-with, X-Custom-Header, Authorization");
        if(!"OPTIONS".equals(method)){
            String adminId ="";
            String path = req.getServletPath();
            String login = "/admin/login";
            String logout = "/admin/logout";
            String check = "/sms/service/check";
            String fileUp = "/file/fileUp";
            RequestWrapper wrapper = new RequestWrapper(req);
            if(!login.equals(path)&&!logout.equals(path)&&!check.equals(path)&&!fileUp.equals(path)){
                if("GET".equals(method)){
                    adminId =request.getParameter("adminId");
                    if(!NumberUtils.isNumber(adminId)){
                        return;
                    }
                }else if("POST".equals(method)){
                    if(!login.equals(path)&& !logout.equals(path) && !logout.equals(path)){
                        String body =  wrapper.getBody();

                        JSONObject json = JSONObject.parseObject(body);
                        if(json==null){
                            return;
                        }
                        adminId=json.getString("adminId");
                        if(!NumberUtils.isNumber(adminId)){
                            return;
                        }
                    }
                }
                Object token = redisService.get(adminId);
                if (token == null) {
//         httpServletResponse.sendRedirect("/admin/loginPage");//返回到没有权限的页面
                    return ;
                }
                //查询改用户所有的权限菜单
                List<MenuDto> menus  = menuService.queryByAdminId(NumberUtils.toLong(adminId));
                List<String> urls = new ArrayList<>();
                for (MenuDto menu:menus) {
                    urls.add(menu.getUrl());
                }
                if(!urls.contains(path)){
//            httpServletResponse.sendRedirect("/admin/loginPage");//返回到没有权限的页面
                    return;
                }
            }
            Object token =adminId+new Date().getTime();
            redisService.set(adminId,token,3000L);
            chain.doFilter(wrapper, res);
        }


    }
    @Override
    public void destroy() {

    }
}
