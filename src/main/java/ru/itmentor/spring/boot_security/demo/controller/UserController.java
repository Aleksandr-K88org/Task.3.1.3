package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    public String getUser(Model model, Principal principal) {
        String name = principal.getName();
        Optional<User> currentUser = userService.findByName(name);

        if (currentUser.isPresent()) {
            model.addAttribute("user", currentUser.get());
            return "users/user";
        } else {
            return "redirect:/error";
        }
    }
}