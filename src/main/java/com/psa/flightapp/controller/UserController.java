package com.psa.flightapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.psa.flightapp.entities.Role;
import com.psa.flightapp.entities.User;
import com.psa.flightapp.repositories.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(path = "/showReg")
	public String showRegistrationPage() {
		return "login/newRegistration";
	}

	@RequestMapping(path = "/showLogin")
	public String showLoginPage() {
		return "login/login";
	}

	@RequestMapping(path = "/saveReg")
	public String saveNewRegistration(@ModelAttribute("user") User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.USER);
		userRepo.save(user);
		return "login/login";
	}

	@RequestMapping("/verifyLogin")
	public String verifyLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelMap modelMap) {
		Optional<User> userOptional = userRepo.findByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getEmail().equals(email) && passwordEncoder.matches(password, user.getPassword())) {
				return "searchFlights";
			}
		}
		modelMap.addAttribute("error", "Invalid username/password");
		return "login/login";
	}
}
