package com.congnghejava.controller.admin;

import com.congnghejava.model.Role;
import com.congnghejava.model.User;
import com.congnghejava.service.RoleService;
import com.congnghejava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@GetMapping("/admin/user")
	public String index(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "admin/user/index";
	}

	@GetMapping("/admin/user/create")
	public String create(Model model) {
		User user = new User();
		user.setRole("user");
		user.setEnabled(true);
		user.setGender(true);
		model.addAttribute("user", user);
		return "admin/user/create";
	}

	@PostMapping("/admin/user/store")
	public String store(@Validated User user, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("error", result.getAllErrors().toString());
			return "redirect:/admin/user/create";
		}

		BCryptPasswordEncoder encodedPassword = new BCryptPasswordEncoder();
		String passHash = encodedPassword.encode(user.getPassword());
		user.setPassword(passHash);
		if (user.getRole().equals("user")) {
			Role role = roleService.getOne(2L);
			Set<Role> roleSet = new HashSet<>();
			roleSet.add(role);
			user.setRoles(roleSet);
		} else {
			Role role = roleService.getOne(1L);
			Set<Role> roleSet = new HashSet<>();
			roleSet.add(role);
			user.setRoles(roleSet);
		}

		userService.save(user);

		redirect.addFlashAttribute("success", "Create user successfully!");

		return "redirect:/admin/user";
	}

	@GetMapping("/admin/user/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		return "admin/user/edit";
	}

	@PostMapping("/admin/user/update/{id}")
	public String update(@PathVariable("id") Long id, User user, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("error", result.getAllErrors().toString());
			return "redirect:/admin/user/edit/" + id.toString();
		}

		userService.update(id, user);

		redirect.addFlashAttribute("success", "Update category successfully!");

		return "redirect:/admin/user/edit/" + id.toString();
	}
}
