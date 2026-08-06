package com.contacts.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.contacts.dao.ContactRepository;
import com.contacts.dao.PaymentRepository;
import com.contacts.dao.UserRepository;
import com.contacts.dto.PaymentRequest;
import com.contacts.dto.PaymentResponse;
import com.contacts.helper.Message;
import com.contacts.model.Contact;
import com.contacts.model.Payment;
import com.contacts.model.User;
import com.contacts.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private StripeService stripeService;

	@Autowired
	private PaymentRepository paymentRepository;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {

		String username = principal.getName();
		System.out.println("Username: " + username);
		User user = (User) this.userRepository.getUserByUsername(username);
		System.out.println("User: " + user);
		model.addAttribute("user", user);

	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("title", "Contacts360 - Dashboard");
		return "user/dashboard";
	}

	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Contacts360 - Add Contact");
		model.addAttribute("contact", new Contact());
		return "user/add_contact";
	}

	@PostMapping("/process-contact")
	public String addContact(@ModelAttribute Contact contact, @RequestParam("contactImage") MultipartFile multipartFile,
			Principal principal, Model model, RedirectAttributes redirectAttributes) {

		try {
			String name = principal.getName();
			User user = (User) userRepository.getUserByUsername(name);

			// Uploading File
			if (multipartFile.isEmpty()) {
				contact.setImage("default.png");
				System.out.println("File is empty!");
			} else {

				String fileName = multipartFile.getOriginalFilename();
				contact.setImage(fileName);

				File file = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(file.getAbsolutePath() + File.separator + fileName);
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			contact.setUser(user);
			user.getContactList().add(contact);
			this.userRepository.save(user);
//			model.addAttribute("message", new Message("Contact added successfully!", "success"));
			redirectAttributes.addFlashAttribute("message", new Message("Contact added successfully!", "success"));
			System.out.println("Contact: " + contact);

		} catch (Exception e) {
//			model.addAttribute("message", new Message("Something went wrong!", "danger"));
			redirectAttributes.addFlashAttribute("message", new Message("Something went wrong!", "danger"));
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/user/add-contact";
	}

	@GetMapping("/show-contact/{page}")
	public String showContact(@PathVariable int page, Model model, Principal principal) {

		model.addAttribute("title", "Contacts360 - Show Contacts");
		String username = principal.getName();
		User user = this.userRepository.getUserByUsername(username);
		Pageable pageable = PageRequest.of(page, 5);
		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());

		return "user/show_contact";
	}

	@GetMapping("/{id}/contact")
	public String contactDetails(@PathVariable("id") int id, Model model, Principal principal) {

		Optional<Contact> optional = this.contactRepository.findById(id);
		Contact contact = optional.get();
		model.addAttribute("title", "Contacts360 - Contact Details");

		String username = principal.getName();
		User user = this.userRepository.getUserByUsername(username);

		if (user.getId() == contact.getUser().getId()) {
			model.addAttribute("contact", contact);
			model.addAttribute("title", "Contacts360 - Contact Details of " + contact.getfName());
		}

		return "user/contact_details";
	}

	@GetMapping("/delete/{id}")
	public String deleteContact(@PathVariable("id") int id, Principal principal,
			RedirectAttributes redirectAttributes) {

		String username = principal.getName();
		User user = this.userRepository.getUserByUsername(username);

		Optional<Contact> optional = this.contactRepository.findById(id);

		if (optional.isPresent()) {
			Contact contact = optional.get();

			if (user.getId() == contact.getUser().getId()) {
				try {
					this.contactRepository.delete(contact);
					redirectAttributes.addFlashAttribute("message",
							new Message("Contact deleted successfully!", "success"));
				} catch (Exception e) {
					redirectAttributes.addFlashAttribute("message", new Message("Something went wrong...", "danger"));
					e.printStackTrace();
				}
			} else {
				redirectAttributes.addFlashAttribute("message",
						new Message("You are not authorized to delete this contact!", "warning"));
			}
		} else {
			redirectAttributes.addFlashAttribute("message", new Message("Contact not found!", "danger"));
		}

		return "redirect:/user/show-contact/0";
	}

	// Update Page

	@PostMapping("/update-contact/{id}")
	public String updateForm(@PathVariable("id") int id, Model model) {

		model.addAttribute("title", "Contacts360 - Contact Details");
		Contact contact = this.contactRepository.findById(id).get();
		model.addAttribute("contact", contact);
		return "user/update_contact";
	}

	// Update Contact
	@PostMapping("/process-update")
	public String updateContact(@ModelAttribute Contact contact,
			@RequestParam("contactImage") MultipartFile multipartFile, Principal principal, Model model,
			RedirectAttributes redirectAttributes) {

		try {
			String name = principal.getName();
			User user = (User) userRepository.getUserByUsername(name);

			Contact oldContact = this.contactRepository.findById(contact.getId()).get();

			// Uploading File
			if (multipartFile.isEmpty()) {
				contact.setImage(oldContact.getImage());
			} else {

				// Delete Old File

				if (!(oldContact.getImage().equals("default.png"))) {
					File deleteFile = new ClassPathResource("static/img").getFile();
					File file = new File(deleteFile, oldContact.getImage());
					file.delete();
				}

				// Update New File
				String fileName = multipartFile.getOriginalFilename();
				contact.setImage(fileName);

				File fileNew = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(fileNew.getAbsolutePath() + File.separator + fileName);
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			contact.setUser(user);
			user.getContactList().add(contact);
			this.userRepository.save(user);
//			model.addAttribute("message", new Message("Contact added successfully!", "success"));
			redirectAttributes.addFlashAttribute("message", new Message("Contact updated successfully!", "success"));
			System.out.println("Contact: " + contact);

		} catch (Exception e) {
//			model.addAttribute("message", new Message("Something went wrong!", "danger"));
			redirectAttributes.addFlashAttribute("message", new Message("Something went wrong!", "danger"));
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/user/" + contact.getId() + "/contact";
	}

	// Your Profile
	@GetMapping("/profile")
	public String yourProfile(Model model) {
		model.addAttribute("title", "Contacts360 - Your Profile");

		return "user/profile";
	}

	// Your Setting
	@GetMapping("/settings")
	public String yourSetting(Model model) {
		model.addAttribute("title", "Contacts360 - Settings");

		return "user/setting";
	}

	// Change Password
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, Model model,
			RedirectAttributes redirectAttributes) {

		model.addAttribute("title", "Contacts360 - Settings");

		try {

			User currentUser = this.userRepository.getUserByUsername(principal.getName());
			System.out.println("Current Pass: " + currentUser.getPassword());
			System.out.println("New Pass: " + newPassword);

			if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {

				currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
				this.userRepository.save(currentUser);
				model.addAttribute("message", new Message("Password changed successfully!", "success"));
				return "user/setting";

			} else {

				redirectAttributes.addFlashAttribute("message", new Message("Wrong old password!", "danger"));
				return "redirect:/user/settings";
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("Something went wrong!", "danger"));
			e.printStackTrace();
			return "redirect:/user/settings";
		}

	}

	// Create Order - Payment Gateway
	@PostMapping("/create-order")
	@ResponseBody
	public PaymentResponse createOrder(@RequestBody PaymentRequest request) {
		System.out.println("Order Function Executed");
		System.out.println("Data: " + request);

		Long amount = request.getAmount();
		System.out.println("Amount: " + amount);

		PaymentResponse response = stripeService.checkout(request);
		System.out.println("Response: " + response);
		return response;
	}

	@GetMapping("/success")
	public String paymentSuccess(@RequestParam("session_id") String sessionId, Model model, Principal principal)
			throws StripeException {

		Session session = stripeService.retrieveSession(sessionId);

		String paymentIntentId = session.getPaymentIntent();
		PaymentIntent paymentIntent = stripeService.retrievePaymentIntent(paymentIntentId);
		System.out.println("Payment Intent: " + paymentIntent);
		System.out.println("Payment Intent JSON: " + paymentIntent.toJson());
		System.out.println("Payment Intent String: " + paymentIntentId.toString());

		Payment payment = new Payment();
		payment.setPaymentIntentId(paymentIntentId);
		payment.setSessionId(sessionId);
		payment.setEmail(session.getCustomerDetails().getEmail());
		payment.setAmount(paymentIntent.getAmount());
		payment.setStatus(paymentIntent.getStatus());
		payment.setCurrency(paymentIntent.getCurrency());
		payment.setCustomerReference(paymentIntent.getPaymentDetails().getCustomerReference());
		payment.setOrderReference(paymentIntent.getPaymentDetails().getOrderReference());
		payment.setCreatedAt(new Date(paymentIntent.getCreated() * 1000));
		payment.setUser(this.userRepository.getUserByUsername(principal.getName()));

		paymentRepository.save(payment);
		System.out.println("Payment Object: " + payment);

		model.addAttribute("title", "Contacts360 - Payment Succeed");
		model.addAttribute("msg", "Payment Successful!");
		model.addAttribute("amount", paymentIntent.getAmount());
		model.addAttribute("status", paymentIntent.getStatus());
		model.addAttribute("email", session.getCustomerDetails().getEmail());
		model.addAttribute("payment", payment);

		return "user/success";
	}

	@GetMapping("/cancel")
	public String paymentCancel(Model model) {
		model.addAttribute("title", "Contacts360 - Payment Failed");
		model.addAttribute("msg", "Payment Canceled!");
		System.out.println("Cancel");
		return "user/cancel";
	}

}
