package com.github.bat333.stockroom.controller;

import com.github.bat333.stockroom.model.DataSector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sector", new DataSector("", "", ""));
        return "index";  // Nome do arquivo HTML, sem a extensão
    }
}
