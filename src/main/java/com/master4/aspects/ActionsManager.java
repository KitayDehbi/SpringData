package com.master4.aspects;

import com.master4.controllers.ArticleController;
import com.master4.controllers.UserController;
import com.master4.entities.Role;
import com.master4.services.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ActionsManager {
    @Autowired
    private UserController userController;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleController articleController;

    @Around("execution(* com.master4.controllers.ArticleController.delete(..))")
    public String deleteArticle (ProceedingJoinPoint jp) throws Throwable {
        Object [] args = jp.getArgs();
        if (userController.getSession().getAttribute("roles") != null) {
            ArrayList<Role> roles = (ArrayList<Role>) userController.getSession().getAttribute("roles");

            if (!roles.contains("admin") && !roles.contains("writter")) {
                System.err.println("no priveleges");
                userController.setError("no priveleges");
            }
            else if(roles.contains("writter") &&
                    userService.isUserArticle(Long.parseLong(userController.getSession().
                            getAttribute("id").toString()) ,Long.parseLong(args[1].toString()) )){
                    System.err.println("he is writter");
                    System.err.println("Article "+Long.parseLong(args[1].toString()));
                    System.err.println("user "+Long.parseLong(userController.getSession().getAttribute("id").toString()));
                    jp.proceed();
                    return articleController.redirect("article");
            }
            else return articleController.redirect("article");
        }
        return userController.redirect(" ");
    }
    @Around("execution(* com.master4.controllers.ArticleController.add(..) )")
    public String addAndModifyArticle(ProceedingJoinPoint joinPoint) throws Throwable
    {
        if (userController.getSession().getAttribute("roles") != null) {
        List<String> roless = (List<String>)userController.getSession().getAttribute("roles");
        if(roless.contains("visitor")){
            System.out.println("No privileges ");
            return userController.redirect("");
        }
        else {
            joinPoint.proceed();
        return null;}
    }
        return userController.redirect("");

}
    @Around("execution(* com.master4.controllers.UserController.add(..))")
    public String addUser(ProceedingJoinPoint jp) throws Throwable {
        if (userController.getSession().getAttribute("roles")!= null){
            Object[] args = jp.getArgs();
            List<String> role = (List<String>) userController.getSession().getAttribute("roles");
            if (role.contains("admin")) {
                jp.proceed();
                return null;
            }
            else return userController.redirect("");
        }
        return userController.redirect("");

    }
    @Around("execution(* com.master4.controllers.UserController.delete(..))")
    public String DeleteUser(ProceedingJoinPoint jp) throws Throwable {
        if (userController.getSession().getAttribute("roles")!= null){
            Object[] args = jp.getArgs();
            List<String> role = (List<String>) userController.getSession().getAttribute("roles");
            if (role.contains("admin")) {
                jp.proceed();
                return userController.redirect("user");
            }
            else return userController.redirect("");
        }
        return userController.redirect("");
        //return userController.add(User(ModelMap(args[0]),User(args[1])));
    }
    @Around("execution(* com.master4.controllers.UserController.edit())")
    public String updateUser(ProceedingJoinPoint jp) throws Throwable {
        if (userController.getSession().getAttribute("roles")!= null){
            Object[] args = jp.getArgs();
            List<String> role = (List<String>) userController.getSession().getAttribute("roles");
            if (role.contains("admin")) {
                jp.proceed();
            }
            else return userController.redirect("");
        }
        return userController.redirect("");

    }
}
