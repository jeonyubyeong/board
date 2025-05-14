package com.example.board.controller;

import com.example.board.dto.UserDTO;
import com.example.board.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String loginForm(@RequestParam(value = "required", required = false) String required, Model model) {
        if ("true".equals(required)) {
            model.addAttribute("needLogin", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session, Model model) {
        UserDTO loginResult = userService.login(userDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getUserEmail());
            return "redirect:/index";
        } else {
            model.addAttribute("loginFail", true);
            return "login";
        }
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO) {
        userService.save(userDTO);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
