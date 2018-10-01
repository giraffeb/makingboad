package com.example.board.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomeController {


    @RequestMapping(value = "/")
    public String home(Map<String, Object> model){

        model.put("message", "Hello new World!");
        return "index";
    }


}
