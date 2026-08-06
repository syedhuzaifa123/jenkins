package com.contacts.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.contacts.dao.UserRepository;
import com.contacts.helper.Message;
import com.contacts.model.User;
import com.contacts.service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserController userController;

	Random random = new Random();

	ForgotController(UserController userController) {
		this.userController = userController;
	}

	@GetMapping("/forgot")
	public String forgotPage(Model model) {
		model.addAttribute("title", "Contacts360 - Forgot Password");
		return "forgot";
	}

	@PostMapping("/send-otp")
	public String sendOtpPage(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes,
			HttpSession session) {

		model.addAttribute("title", "Contacts360 - Forgot Password");
		System.out.println("Email: " + email);
		int otp = 100000 + random.nextInt(900000);
		System.out.println("OTP: " + otp);

		try {
			String from = "sheheryarmoazzam99@gmail.com";
			String subject = "OTP From Contact360";
//			String message = "The computer generated OTP is: " + otp;

			String message = "<div style='border:1px solid #e2e2e2; padding:20px'>" + "<h3>" + "Your OTP is: <b> " + otp
					+ "</h3>" + "</div>";

			boolean result = this.emailService.sendEmail(email, from, subject, message);

			if (result) {
				redirectAttributes.addFlashAttribute("message",
						new Message("OTP sent successfully! Please check your email", "success"));
				session.setAttribute("generatedOtp", otp);
				session.setAttribute("email", email);

				return "redirect:/verify-otp";
			} else {

				redirectAttributes.addFlashAttribute("message", new Message("Email not sent! Try again", "danger"));
				redirectAttributes.addFlashAttribute("redirectUrl", "/forgot");
				return "redirect:/verify-otp";

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", new Message("Something went wrong! Try again", "danger"));
			redirectAttributes.addFlashAttribute("redirectUrl", "/forgot");
			return "redirect:/verify-otp";

		}

	}

	@GetMapping("/verify-otp")
	public String verifyOtpPage(Model model) {
		model.addAttribute("title", "Contacts360 - Verify OTP");
		return "verify-otp";
	}

	@PostMapping("/process-otp")
	public String processOtp(@RequestParam("otp") int otp, HttpSession session, RedirectAttributes redirectAttributes) {

		int genOtp = (int) session.getAttribute("generatedOtp");
		String email = (String) session.getAttribute("email");

		if (genOtp == otp) {

			User user = this.userRepository.getUserByUsername(email);

			if (user == null) {

				redirectAttributes.addFlashAttribute("message", new Message("This user is not exist", "danger"));
				redirectAttributes.addFlashAttribute("redirectUrl", "/signin");
				return "redirect:/verify-otp";

			} else {
				redirectAttributes.addFlashAttribute("message", new Message("OTP verified successfully!", "success"));
				redirectAttributes.addFlashAttribute("redirectUrl", "/change-password");
				return "redirect:/verify-otp";
			}
		} else {

			redirectAttributes.addFlashAttribute("message", new Message("Incorrect OTP", "danger"));
			redirectAttributes.addFlashAttribute("redirectUrl", "/verify-otp");
			return "redirect:/verify-otp";

		}
	}

	@GetMapping("/change-password")
	public String changePassword(Model model) {

		model.addAttribute("title", "Contacts360 - Change Password");
		return "change-password";
	}

	@PostMapping("/new-password")
	public String newPassword(@RequestParam("newPassword") String password, HttpSession session,
			RedirectAttributes redirectAttributes) {
 
		String email = (String) session.getAttribute("email");
		User user = this.userRepository.getUserByUsername(email);
		user.setPassword(this.bCryptPasswordEncoder.encode(password));
		this.userRepository.save(user);

		redirectAttributes.addFlashAttribute("message", new Message("Your password has been changed", "success"));
		redirectAttributes.addFlashAttribute("redirectUrl", "/signin");
		return "redirect:/change-password";

	}

}
