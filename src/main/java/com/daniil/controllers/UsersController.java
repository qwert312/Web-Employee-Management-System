package com.daniil.controllers;

import com.daniil.entities.User;
import com.daniil.entities.enums.Role;
import com.daniil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String usersGet() {
        return "redirect:/users/1";
    }

    @GetMapping("/users/{pageNumber}")
    public String usersPageGet(Model page,
                                   @PathVariable int pageNumber) {
        if (pageNumber <= 0) {
            return "redirect:/users/1";
        }

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 12);
        Page<User> currentPage = userService.findAllUsersPageable(pageRequest);

        if (currentPage.getContent().isEmpty() && pageNumber > 1) {
            return "redirect:/users/1";
        }

        page.addAttribute("users", currentPage.getContent());
        page.addAttribute("pageNumber", pageNumber);
        page.addAttribute("totalPages", currentPage.getTotalPages());
        return "users";
    }

    @DeleteMapping("/users/user")
    public String userDelete(@RequestParam(value = "id") long id,
                             @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber) {
        userService.deleteUser(id);
        return "redirect:/users/" + pageNumber;
    }

    @GetMapping("/users/user")
    public String userGet(Model page,
                              @RequestParam(value = "id", required = false) Long id,
                              @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber) {
        User user = new User();
        String action = "add";

        if (id != null) {
            user = userService.findUserById(id).get();
            action = "view";
        }

        page.addAttribute("user", user);
        page.addAttribute("action", action);
        page.addAttribute("pageNumber", pageNumber);
        page.addAttribute("possibleRoles", Role.values());
        return "user";
    }

    @PostMapping("/users/user")
    public String userAdd(Model page,
                              @ModelAttribute User user,
                              @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber) {
        userService.saveUser(user);
        return "redirect:/users/" + pageNumber;
    }
}
