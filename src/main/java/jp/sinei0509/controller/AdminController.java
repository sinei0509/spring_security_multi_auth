package jp.sinei0509.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

  @GetMapping("/admin/login")
  public String login() {
    return "/admin/login";
  }

  @GetMapping("/admin/hello")
  public String hello(Model model) {
    Object o = SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
    User user = (User) o;

    model.addAttribute("userid", user.getUsername());
    return "/admin/hello";
  }
}
