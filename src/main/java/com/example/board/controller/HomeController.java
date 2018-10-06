package com.example.board.controller;


import com.example.board.dao.BoardDao;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.*;

@Controller
public class HomeController {

    @Autowired
    private BoardService service;

    @RequestMapping(value = "/")
    public String home(Map<String, Object> model){

        List<Map<String, Object>> boardList = service.getList();

        model.put("message", "Hello new World!");
        model.put("list",boardList);

        return "index";
    }


    @RequestMapping(value="/list")
    public String getList(Map<String, Object> model,
                          @RequestParam Map<String, Object> params){

            List<Map<String, Object>> list = service.getList();

            model.put("list", list);

        return "list";
    }

    @RequestMapping(value = "/post")
    public String getPost(Map<String, Object> model,
                          @RequestParam Map<String, Object> params){

        Map<String, Object> post = service.getPost(params);

        model.put("post", post);

        return "post";
    }

    @RequestMapping(value = "/writePost", method = RequestMethod.GET)
    public String writePostForm(Map<String, Object> model,
                            @RequestParam Map<String, Object> params){

        return "writePost";
    }

    @RequestMapping(value="/writePost", method = RequestMethod.POST)
    public String postWrite(Map<String, Object> model,
                            @RequestParam Map<String, Object> params){

        service.writePost(params);

        List<Map<String,Object>> list = service.getList();

        model.put("list", list);

        return "list";
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.GET)
    public String updatePostForm(Map<String, Object> model,
                            @RequestParam Map<String, Object> params){

        Map<String, Object> post = service.getPost(params);

        model.put("post", post);

        return "updatePost";
    }


    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    public String updatePost(Map<String, Object> model,
                                 @RequestParam Map<String, Object> params){

        service.updatePost(params);

        return "redirect:/list";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String getLoginPage(Map<String, Object> map,
                               @RequestParam Map<String, Object> params){

        return "login";
    }

    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public String getLoginCheck(Map<String,Object> model,
                                HttpSession session,
                                @RequestParam Map<String, Object> params){

        String jsp = "login";

        boolean result = service.loginCheck(params);

        if(result == true){
            session.setAttribute("loginStatus", "Hello This is Session.");
            jsp = "redirect:/list";
        }else{
            model.put("result", "잘못된 계정 정보입니다. ");
        }


        return jsp;
    }
}
