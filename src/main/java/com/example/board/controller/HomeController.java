package com.example.board.controller;


import com.example.board.dao.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private final BoardDao dao;

    public HomeController(BoardDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/")
    public String home(Map<String, Object> model){

        List<Map<String, Object>> boardList = dao.getList();

        model.put("message", "Hello new World!");
        model.put("list",boardList);

        return "index";
    }


}
