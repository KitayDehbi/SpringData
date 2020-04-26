package com.master4.controllers;

import com.master4.entities.Article;
import com.master4.entities.Role;
import com.master4.entities.Tag;
import com.master4.entities.User;
import com.master4.exceptions.ResourceNotFoundException;
import com.master4.services.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/"})

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = {"/user","/user/page/{id}"})
    public String home(@PathVariable(name="id",required = false) Optional<Integer> id, ModelMap model){
        Page<User> pages = userService.getAllUsers(id, 3, "id");
        model.addAttribute("pageable", pages);
        return "user/home";
    }
    @GetMapping(value = {""})
    public String index( ModelMap model ,User user){
        model.addAttribute("user", user);
        return "user/index";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, BindingResult result, ModelMap model , HttpSession session) throws ResourceNotFoundException {
        if(result.hasErrors()){
            model.addAttribute("user",user);
            System.out.println(result);
            return "user/index";
        }
        if(userService.isExist(user.getEmail(),user.getPassword())){
            session.setAttribute("roles", userService.getRolesOfUserByEmail(user.getEmail()));
            System.out.println("session ------------------------------");

            List<Role> liste = (List<Role>) session.getAttribute("roles");
            for( Role r : liste) System.out.println(r.getName());
            return "redirect:/article/";
        }
        return  "user/index";
    }
    @GetMapping("/user/add")
    public String add(ModelMap model, User user) {
        model.addAttribute("user", user);
        return "user/add";
    }

    @PostMapping("/user/save")
    public String saveArticle(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model) throws ResourceNotFoundException {
        if(result.hasErrors()){
            model.addAttribute("user",user);
            System.out.println("has error");
            return "user/add";
        }
        userService.save(user);
        return "redirect:/user/";
    }
    @Pointcut("execution( String com.master4.controllers.UserController.view(id,model))")
    @RequestMapping("/user/view/{id}")
    public String view(@PathVariable("id") long id,ModelMap model) throws ResourceNotFoundException {
        model.addAttribute("user",userService.findById(id));
        model.addAttribute("articles" , userService.getArticlesOfUser(id));
        return "user/view";
    }

    @GetMapping("/user/delete/{page}/{id}")
    public String delete(@PathVariable("page") long page,@PathVariable("id") long id, ModelMap model) throws ResourceNotFoundException {
        userService.deleteById(id);
        return "redirect:/user/page/"+page;
    }

    @GetMapping("/user/add/{id}")
    public String edit(@PathVariable("id") long id, ModelMap model) throws ResourceNotFoundException {
        User user = userService.findById(id);
        model.addAttribute("user", user);


        return "user/add";
    }
}
