package com.example.board.post;


import com.example.board.comments.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Map;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    CommentsRepository commentsRepository;


    @GetMapping(value = {"/", "/list"})
    public String getPostAll(@RequestParam Map<String, Object> params,
                             Model model){

        if(params.get("pageSize") == null){
            params.put("pageSize", 9); // 0-9 10개
        }

        int maxPageNumber = postService.getMaxPageNumber(params);

        model.addAttribute("list", postService.getPostAll(params));
        model.addAttribute("maxPageNumber", maxPageNumber);

        return "list";
    }

    @GetMapping("/post")
    public String getPost(@RequestParam Map<String, Object> params,
                          Model model,
                          HttpSession session){

        Post post = null;
        try{
            post = postService.getPost(params, session);
        }catch (Exception e){
            return "redirect:/";
        }



        model.addAttribute("post", post);
        model.addAttribute("comments_list", commentsRepository.findAllByPost(post));

        return "post";
    }

    @GetMapping("/post/{post_id}")
    public String getPost(@PathVariable(value = "post_id") Integer postId){

        return "post";
    }


    @GetMapping("/updatePost")
    public String getUpdatePage(@RequestParam Map<String, Object> params,
                                HttpSession session,
                                Model model){
        Post post = null;
        try{
            post = postService.getPost(params, session);

        }catch(Exception e){
            return "redirect:/";
        }
        model.addAttribute("post", post);

        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@RequestParam Map<String, Object> params,
                          HttpSession session){
        System.out.println(params);
        try{
            postService.updatePost(params, session);
        }catch (Exception e){
            e.printStackTrace();
            //TODO: 업데이트 실패하는 경우가 있을까?
            return "redirect:/";
        }

        return "redirect:/";
    }

    @PostMapping("/deletePost")
    public String deletePost(@RequestParam Map<String, Object> params,
                             HttpSession session){

        postService.deletePost(params, session);

        return "redirect:/";

    }

    @GetMapping("/write")
    public String getWriteForm(){

        return "/writePost";
    }

    @PostMapping("/writePost")
    public String writePost(@RequestParam Map<String, Object> params,
                            HttpSession session){
        System.out.println(params);
        postService.writePost(params, session);

        return "redirect:/";
    }


}
