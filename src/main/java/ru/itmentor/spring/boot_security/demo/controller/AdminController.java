package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String allUsers(Model model) {
        Set<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/new")
    public String createUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRole", roleService.findAll());
        return "users/create";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, Model model) {
        Optional<User> userByName = userService.findByName(user.getName());
        if (userByName.isPresent()) {
            bindingResult.rejectValue("name", "error name",
                    "Пользователь с таким именем уже зарегистрирован");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("listRole", roleService.findAll());
            return "users/create";
        }

        this.userService.save(user);
        return "redirect:/admin/";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam("id") Long id, Model model) {
        Optional<User> userById = userService.findById(id);

        if (userById.isPresent()) {
            model.addAttribute("user", userById.get());
            model.addAttribute("listRole", roleService.findAll());
            return "users/edit";
        } else {
            return "redirect:/admin/";
        }
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult, Model model) {
        Optional<User> userByName = userService.findByName(user.getName());
        if (userByName.isPresent() && (!userByName.get().getId().equals(user.getId()))) {
            bindingResult.rejectValue("name", "error name",
                    "Пользователь с таким именем уже зарегистрирован");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("listRole", roleService.findAll());
            return "users/edit";
        }

        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }
}