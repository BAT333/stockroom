package com.github.bat333.stockroom.controller;



import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {

    @GetMapping("/ui/part")
    public String forwardToPart(HttpServletRequest request) {
        request.setAttribute(RequestDispatcher.FORWARD_REQUEST_URI, "/api/part");
        return "forward:/api/part";
    }


}
