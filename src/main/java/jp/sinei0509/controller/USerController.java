package jp.sinei0509.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class USerController {

  @GetMapping("/login")
  public String login() {
    return "/login";
  }

  @GetMapping("/hello")
  public String hello(Model model) {
    Object o = SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
    User user = (User) o;

    model.addAttribute("userid", user.getUsername());
    return "/hello";
  }
}
