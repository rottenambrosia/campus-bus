package com.kgec.campusbus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles the landing page and terms & conditions.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }
}
