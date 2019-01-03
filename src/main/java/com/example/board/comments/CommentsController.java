package com.example.board.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class CommentsController {

    @Autowired
    CommentsService commentsService;


    @PostMapping("/postComments")
    public String postComments(@RequestParam Map<String, Object> params,
                               Model model,
                               HttpSession session){

        commentsService.saveComments(params, session);

        return "redirect:/post?post_id="+params.get("post_id");
    }
}
