package com.mersiades.awcweb.controllers;

import com.mersiades.awcdata.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping({ "", "/", "/index"})
    public String listOwners(Model model) {
        model.addAttribute("users", userService.findAll());

        return "users/index";
    }
}
