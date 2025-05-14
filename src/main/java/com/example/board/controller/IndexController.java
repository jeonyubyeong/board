package com.example.board.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/?required=true";
        }
        String userEmail = (String) session.getAttribute("loginEmail");
        model.addAttribute("userEmail", userEmail);
        return "index";
    }
}
