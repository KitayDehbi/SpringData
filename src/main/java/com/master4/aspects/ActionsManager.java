package com.master4.aspects;

import com.master4.controllers.ArticleController;
import com.master4.controllers.UserController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ActionsManager {
    @Autowired
    private UserController userController;

    @Around("execution(* com.master4.controllers.UserController.delete(..))")
    public String hello(ProceedingJoinPoint jp) throws Throwable {
        Object[] args=jp.getArgs();
        System.err.println(args);
        if(!true) {
            Object r = jp.proceed(args);
            return r.toString();

        }else{
            return userController.redirect(" ");
        }
    }

}
