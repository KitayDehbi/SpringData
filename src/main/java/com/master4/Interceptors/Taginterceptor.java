package com.master4.Interceptors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Taginterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception {
        RequestMapping rm = ((HandlerMethod) handler).getMethodAnnotation(
                RequestMapping.class);

        List<String > roles = (List<String>) request.getSession().getAttribute("roles");
        boolean alreadyLoggedIn = request.getSession().getAttribute("user") != null;
        if(!roles.contains("admin")){
            System.out.println("admin want to do something");
            response.sendRedirect(request.getContextPath()+"/tag/");
            return false;
        }
        else if(!alreadyLoggedIn){
            response.sendRedirect(request.getContextPath()+"/");
        }

        return true;
    }
}
