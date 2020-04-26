package com.master4.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
// test Class
// we have a pointcut in the view method of the userController
public class ActionsManager {
    @Before("com.master4.controllers.UserController.view(long, org.springframework.ui.ModelMap)")
    public void beforeDelete(){
        System.out.println("----------------before view -------------------------------");
    }
}
