package com.poly.SOF3021.user;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.SOF3021.model.Account;
import com.poly.SOF3021.model.Mail;
import com.poly.SOF3021.repository.AccountRepository;
import com.poly.SOF3021.service.SuportService;

@Controller
public class loginController {
	@Autowired
	AccountRepository dao ;
	@Autowired
	SuportService sp ;
	@Autowired
    PasswordEncoder passwordEncoder;
 @GetMapping("/login")
 public String login() {
	 
	 return "/site/user/login";
 }
 
 
 
 @GetMapping("/logoff")
 public String logoff() {
	 
	 return "redirect:/login";
 }
 
 @GetMapping("/403")
 @ResponseBody
 	public String loginError() {
	 return "<h1>Không cấp quyền truy cập cho tài khoản này...</h1>";
 }
 
 @GetMapping("/login/fail")
 public String longinFall() {
	 return "redirect:/login";
 }
 
 @GetMapping("/register")
 public String registerForm(Model model) {
	 Account   account = new Account() ;
	 model.addAttribute("account", account);
	 return "/site/user/register";
 }
 @GetMapping("/forgot-password")
 public String forgot(Model model) {
	 Account   account = new Account() ;
	 model.addAttribute("account", account);
	 return "/site/user/fogotPassword";
 }
 
 @PostMapping("/register")
 public String register(Model model ,@ModelAttribute("Account") Account account , @ModelAttribute("email")String email) {
	 if(!dao.existsById(account.getUsername())) {
		 account.setPassword(passwordEncoder.encode(account.getPassword()));
		 dao.save(account);
		 
		 Mail mail = new Mail();
         mail.setSubject("ĐẶT LẠI MẬT KHẨU");
         mail.setContent("Email đăng ký" + email);
         mail.setMailFrom("");
         mail.setSendTo(email);
         try {
			sp.sendEmail(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
         model.addAttribute("message", "tạo tài khoản thành công");
		 return "redirect:/login";
	 }
	 else {
		model.addAttribute("message", "tài khoản đẫ tồn tại");
	}
	 
	 return "redirect:/login";
 }
 
 @PostMapping("/forgot-password")
 public String findPassword(@ModelAttribute("username")String username,
                            @ModelAttribute("email")String email,
                            RedirectAttributes redirect){
     Optional<Account> account = dao.findById(username);
     if (account.isEmpty()){
         redirect.addFlashAttribute("message", "Username không tồn tại");
     }else {
         if (!email.equalsIgnoreCase(account.get().getEmail())){
             redirect.addFlashAttribute("message", "Email không đúng. Bạn vui lòng nhập đúng thông tin");
         }else {
             String newPass = ThreadLocalRandom.current().nextInt(1000,9999) + "";
             account.get().setPassword(passwordEncoder.encode(newPass));
             dao.save(account.get());
             Mail mail = new Mail();
             mail.setSubject("ĐẶT LẠI MẬT KHẨU");
             mail.setContent("Mật khẩu hiện tại của bạn là: <p style=\"font-weight: bold\">" + newPass + " </p>." +
                     " Vui lòng đặt lại mật khẩu để bảo mật. Xin cảm ơn!");
             mail.setMailFrom("ADMIN");
             mail.setSendTo(account.get().getEmail());
             try{
                 sp.sendEmail(mail);
             }catch (Exception e){
                 e.printStackTrace();
             }
         }
     }
     return "redirect:/2handshop.vn";
 }
}
