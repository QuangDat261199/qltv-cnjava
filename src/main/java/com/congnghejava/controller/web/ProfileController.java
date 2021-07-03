package com.congnghejava.controller.web;


import com.congnghejava.model.Borrow;
import com.congnghejava.model.User;
import com.congnghejava.service.BorrowService;
import com.congnghejava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {
    @Autowired
    UserService userService;

    @Autowired
    BorrowService borrowService;

    @GetMapping("/user/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getByUserName(userDetails.getUsername());
        user.setPassword("");
        model.addAttribute("user", user);
        return "web/profile/index";
    }

    @PostMapping("/user/profile")
    public String update(
            @Validated User user,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirect
    ) {
        User u = userService.getByUserName(userDetails.getUsername());
        if (user.getPassword().equals("")) {
            user.setPassword(u.getPassword());
        } else {
            BCryptPasswordEncoder encodedPassword = new BCryptPasswordEncoder();
            String passHash = encodedPassword.encode(user.getPassword());
            user.setPassword(passHash);
        }

        userService.update(u.getId(), user);
        redirect.addFlashAttribute("success", "Updated profile successfully!");

        return "redirect:/user/profile";
    }

    @GetMapping("/user/borrow")
    public String borrow(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.getByUserName(userDetails.getUsername());
        Borrow borrow = borrowService.getByUser(user);

        model.addAttribute("borrow", borrow);
        return "web/profile/borrow";
    }
}
