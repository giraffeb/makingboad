package com.example.board.controller;


import com.example.board.Users.UsersRepository;
import com.example.board.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    @Qualifier("postService")
    PostService postService;




    @RequestMapping(value = "/uploadfiles")
    public String uploadFiles(Map<String, Object> model){


        return "uploadFiles";
    }

    @RequestMapping(value = "/uploadfiles", method = RequestMethod.POST)
    public String uploadFiles(@RequestParam("files") MultipartFile files[]){
        for(MultipartFile file: files){
            if(!file.getOriginalFilename().isEmpty()){
                String originalFilename = file.getOriginalFilename();

                String filename = originalFilename.substring(0, originalFilename.length()-4);
                String suffix = originalFilename.substring(originalFilename.length()-4, originalFilename.length());

                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filename+"."+suffix)))) {
                    bos.write(file.getBytes());
                    bos.flush();
                    bos.close();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return "redirect:/list";
    }



}
