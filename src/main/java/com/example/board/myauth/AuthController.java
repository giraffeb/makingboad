package com.example.board.myauth;


import com.example.board.Users.UsersRepository;
import com.example.board.Users.Users;
import com.example.board.Users.UsersRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private UsersRepository userRepository;


    @GetMapping("/login")
    public String getLoginPage(HttpSession session){
        if(session.getAttribute("username") != null){
            return "redirect:/";
        }

        return "login";
    }

    @PostMapping("/login")
    public String getLoginPost(@RequestParam Map<String, Object> params,
                               Model model,
                               HttpSession session){
        String username = (String)params.get("username");
        String password = (String)params.get("password");

        Users loginUser = userRepository.findUserByUsername(username).get();
        if(loginUser != null){
            if(loginUser.getPassword().equals(password)){
                session.setAttribute("username", username);
                return "redirect:/";
            }
        }

        return "login";
    }

    @PostMapping("/logout")
    public String getLogout(@RequestParam Map<String, Object> params,
                            HttpSession session){
        session.removeAttribute("username");

        return "redirect:/";
    }


    @GetMapping("/signup")
    public String getSignUpPage(Model model){
        return "signUp";
    }


    @PostMapping("/signup")
    public String signUpPost(@Valid @ModelAttribute("users") UsersRequestDto usersRequestDto,
                             BindingResult result,
                             Model model){
        if(result.hasErrors()){
            result.getFieldErrors().stream()
                    .forEach(e->model.addAttribute(e.getField(), e.getDefaultMessage()));

            result.getFieldErrors()
                    .stream()
                    .forEach((e)-> System.out.println("ERROR : "+e.getField()+" : "+e.getDefaultMessage()));


            return "signUp";
        }
        userRepository.save(usersRequestDto.toEntity());


        return "";
    }



}
