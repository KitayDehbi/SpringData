package com.master4.Interceptors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle (HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object handler) throws Exception {
            RequestMapping rm = ((HandlerMethod) handler).getMethodAnnotation(
                    RequestMapping.class);
            boolean alreadyLoggedIn = request.getSession()
                    .getAttribute("user") != null;
            boolean loginPageRequested = rm != null && rm.value().length > 0
                   && "login".equals(rm.value()[0]);
            if (alreadyLoggedIn && loginPageRequested) {
                //System.out.println(request.getRequestURI());
                response.sendRedirect(request.getRequestURI());
                return false;
            } else if (!alreadyLoggedIn &&!loginPageRequested) {

                response.sendRedirect(request.getContextPath() + "/");
                return false;
            }

            return true;
        }
}
