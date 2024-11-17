package com.daniil.controllers;

import com.daniil.entities.User;
import com.daniil.entities.enums.Role;
import com.daniil.security.UserDetailsImpl;
import com.daniil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;

@Controller
public class ProfileController {
    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profileGet(Model page) {
        User user = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        page.addAttribute("user", user);
        page.addAttribute("action", "profile");
        page.addAttribute("possibleRoles", Role.values());
        return "user";
    }

    @PatchMapping("/profile")
    public String profileChange(Model page,
                                @ModelAttribute User user) {
        userService.changeUser(user);
        return "redirect:/logout";
    }
}
