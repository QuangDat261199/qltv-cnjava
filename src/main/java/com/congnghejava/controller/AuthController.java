package com.congnghejava.controller;

import com.congnghejava.model.Role;
import com.congnghejava.model.User;
import com.congnghejava.service.EmailService;
import com.congnghejava.service.RoleService;
import com.congnghejava.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class AuthController {
	@Autowired
	UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	RoleService roleService;

	private boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.
				isAssignableFrom(authentication.getClass())) {
			return false;
		}
		return authentication.isAuthenticated();
	}

	@GetMapping("/403")
	public String error403() {
		return "/error/403";
	}

	@GetMapping("redirect")
	public String redirect() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role =  auth.getAuthorities().toString();
		if (role.contains("ADMIN")) {
			return "redirect:/admin/dashboard";
		}
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		if (isAuthenticated()) {
			return "redirect:/";
		}
		return "auth/login";
	}

	@GetMapping("/logout")
	public String customLogout(HttpServletRequest request, HttpServletResponse response) {
		if (!isAuthenticated()) {
			return "redirect:/login";
		}

		// Get the Spring Authentication object of the current request.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// In case you are not filtering the users of this request url.
		if (authentication != null){
			new SecurityContextLogoutHandler().logout(request, response, authentication); // <= This is the call you are looking for.
		}
		return "redirect:/login?logout";
	}

	@GetMapping("/forgot-password")
	public String forgotPassword() {
		return "auth/forgot-password";
	}

	@PostMapping("/sent-mail-forgot-password")
	public String sentMailForgotPassword(RedirectAttributes redirect, @RequestParam("email") String email, HttpServletRequest request) {
		User user = userService.getByEmail(email);
		if (user == null) {
			redirect.addFlashAttribute("error", "Email không tồn tại trong hệ thống!");
			return "redirect:/forgot-password";
		}
		RandomString token = new RandomString(20);
		userService.resetToken(user.getId(), token.nextString());

		String link = request.getHeader("host");
		link = "http://" + link + "/reset-password/"+ token.nextString() +"?email=" + user.getEmail();

		Map<String, Object> model = new HashMap<>();
		model.put("name", user.getName());
		model.put("link", link);
		boolean sendMail = emailService.sendEmailForgotPassword(email, model);

		if (sendMail) {
			redirect.addFlashAttribute("success", "Vui lòng kiểm tra Email!");
		} else {
			redirect.addFlashAttribute("error", "Đã xảy ra lỗi, vui lòng liên hệ quản trị viên.");
		}


		return "redirect:/forgot-password";
	}

	@GetMapping("/reset-password/{token}")
	public String resetPassword(Model model, @PathVariable("token") String token, @RequestParam("email") String email) {
		model.addAttribute("token", token);
		model.addAttribute("email", email);
		return "auth/reset-password";
	}

	@PostMapping("/update-password/{token}")
	public String reset(
			@PathVariable("token") String token,
			@RequestParam("password") String password,
			@RequestParam("confirm") String confirm,
			@RequestParam("email") String email,
			RedirectAttributes redirect
	) {
		User user = userService.getByEmail(email);
		if (user == null || user.getResetToken().equals(token)) {
			redirect.addFlashAttribute("error", "Bạn đang cố lương lẹo với chúng tôi");
			return "redirect:/forgot-password";
		}

		if (!password.equals(confirm)) {
			redirect.addFlashAttribute("error", "Nhập lại mật khẩu không chính xác");
			return "redirect:/forgot-password";
		}

		BCryptPasswordEncoder encodedPassword = new BCryptPasswordEncoder();
		String passHash = encodedPassword.encode(password);
		user.setPassword(passHash);
		user.setResetToken(null);
		userService.save(user);

		redirect.addFlashAttribute("success", "Khôi phục mật khẩu thành công");

		return "redirect:/reset-password/" + token + "?email=" + email;
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "auth/register";
	}

	@PostMapping("/register")
	public String store(
		@Validated User user, RedirectAttributes redirect
	) {
		BCryptPasswordEncoder encodedPassword = new BCryptPasswordEncoder();
		String passHash = encodedPassword.encode(user.getPassword());
		user.setPassword(passHash);

		Role role = roleService.getOne(2L);
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(role);
		user.setRoles(roleSet);

		user.setRole("user");
		user.setEnabled(true);
		user.setGender(true);

		userService.save(user);

		redirect.addFlashAttribute("success", "Create user successfully!");

		return "redirect:/register";
	}
}
