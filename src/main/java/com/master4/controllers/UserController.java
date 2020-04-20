package com.master4.controllers;

import com.master4.entities.Article;
import com.master4.entities.Tag;
import com.master4.entities.User;
import com.master4.exceptions.ResourceNotFoundException;
import com.master4.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"","/user"})
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = {"/","/page/{id}"})
    public String home(@PathVariable(name="id",required = false) Optional<Integer> id, ModelMap model){
        Page<User> pages = userService.getAllUsers(id, 3, "id");
        model.addAttribute("pageable", pages);
        return "user/home";
    }
    @GetMapping("/add")
    public String add(ModelMap model, User user) {
        model.addAttribute("user", user);
        return "user/add";
    }
    @PostMapping("/save")
    public String saveArticle(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model) throws ResourceNotFoundException {
        if(result.hasErrors()){
            model.addAttribute("user",user);
            System.out.println("has error");
            return "user/add";
        }
        userService.save(user);
        return "redirect:/user/";
    }
    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") long id,ModelMap model) throws ResourceNotFoundException {
        model.addAttribute("user",userService.findById(id));
        model.addAttribute("articles" , userService.getArticlesOfUser(id));
        return "user/view";
    }
    @GetMapping("/delete/{page}/{id}")
    public String delete(@PathVariable("page") long page,@PathVariable("id") long id, ModelMap model) throws ResourceNotFoundException {
        userService.deleteById(id);
        return "redirect:/user/page/"+page;
    }
    @GetMapping("/add/{id}")
    public String edit(@PathVariable("id") long id, ModelMap model) throws ResourceNotFoundException {
        User user = userService.findById(id);
        model.addAttribute("user", user);


        return "user/add";
    }
}
