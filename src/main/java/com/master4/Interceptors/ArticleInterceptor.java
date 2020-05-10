package com.master4.Interceptors;

import com.master4.entities.User;
import com.master4.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ArticleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception {
        RequestMapping rm = ((HandlerMethod) handler).getMethodAnnotation(
                RequestMapping.class);
        List<String > roles = (List<String>) request.getSession().getAttribute("roles");
        User u=(User) request.getSession().getAttribute("user");
        List<Long> article = (List<Long>) request.getSession().getAttribute("articles");
        boolean alreadyLoggedIn = request.getSession()
                .getAttribute("user") != null;
        boolean saveArticleRequested = rm != null && rm.value().length > 0
                && "/add".equals(rm.value()[0]);
        boolean updateArticleRested = rm != null && rm.value().length > 0
                && "/add/{id}".equals(rm.value()[0]);
        boolean deleteArticleRested = rm != null && rm.value().length > 0
                && "/delete/{page}/{id}".equals(rm.value()[0]);
        if(roles.contains("visitor") && (deleteArticleRested || updateArticleRested|| saveArticleRequested)){
            response.sendRedirect(request.getContextPath() + "/article");
            return false;
        }
        if (alreadyLoggedIn && saveArticleRequested &&(roles.contains("writter") ||roles.contains("admin")) ) {
            System.out.println("writer or admin");
            System.out.println(request.getRequestURI());
            //response.sendRedirect(request.getRequestURI());


        }else if(alreadyLoggedIn && updateArticleRested && roles.contains("admin")) {
            System.out.println(" admin want to modify");
            System.out.println(request.getRequestURI());
            //response.sendRedirect(request.getRequestURI());

        }
        else if (alreadyLoggedIn && updateArticleRested && roles.contains("writter")  ){
            String[] s = request.getRequestURI().split("/");
            System.out.println(u.getId());
            System.out.println(Long.parseLong(s[4]));
            if(!article.contains(Long.parseLong(s[4]))){
                System.out.println(" writer want to modify");
                response.sendRedirect(request.getContextPath() + "/article");
            }
        }else if(alreadyLoggedIn && deleteArticleRested && roles.contains("admin")) {
            System.out.println(" admin want to delete");
            System.out.println(request.getRequestURI());
            //response.sendRedirect(request.getRequestURI());
        }
            else if (alreadyLoggedIn && deleteArticleRested && roles.contains("writter")  ){
                String[] s2 = request.getRequestURI().split("/");
                System.out.println(u.getId());
                System.out.println(Long.parseLong(s2[5]));
                if(!article.contains(Long.parseLong(s2[5]))){
                    System.out.println(" writer want to delete");
                    response.sendRedirect(request.getContextPath() + "/article");
                    return false;
                }


        }else if (!alreadyLoggedIn ){
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }


        return true;
    }
}

