package cun.yun.card.admin.interceptor;

import cun.yun.card.admin.dal.dto.MenuDto;
import cun.yun.card.admin.dal.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private MenuService menuService;
    /**
     * 请求路径之前进行判断
     * @param httpServletResponse
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (HandlerMethod.class.isInstance(handler)) {
            String uri = request.getRequestURI();
            Integer adminId = (Integer) request.getSession(true).getAttribute("ADMIN_USER_ID");
            if (adminId == null) {
                return false;
            }
            //查询改用户所有的权限菜单
            List<MenuDto> menus  = menuService.queryByAdminId(adminId);
            List<String> urls = new ArrayList<>();
            for (MenuDto menu:menus) {
                urls.add(menu.getUrl());
            }
            if(!urls.contains(uri)){
    //            httpServletResponse.sendRedirect("/admin/loginPage");//返回到没有权限的页面
                return false;
            }
            request.getSession(true).setAttribute("ADMIN_USER_ID",adminId);
            request.getSession(true).setMaxInactiveInterval(1800);
        }
        return true;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURI();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
