package com.example.board;


import com.example.board.Users.UserType;
import com.example.board.Users.UserTypeRepository;
import com.example.board.Users.Users;
import com.example.board.Users.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * TODO: 회원가입에 필요한 정보로 교체하는게 좋겠다.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserTest {

    private String testUserId;
    private String testUsername;
    private String testPassword;

    @Autowired
    MockMvc mvc;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Before
    public void setting(){
        this.testUserId = "testusername";
        this.testUsername = "testusername";
        this.testPassword = "1234!abc";
    }


    @Test
    @Transactional
    public void 회원가입하기() throws Exception {
        mvc.perform(
                post("/signup")
                .param("userId",this.testUserId)
                .param("username",this.testUsername)
                .param("password", this.testPassword)
                ).andExpect(status().is3xxRedirection())
                .andDo(print());
    }


    @Test
    @Transactional
    public void 회원삭제(){
        usersRepository.deleteUsersByUsername(this.testUserId);
    }


    @Test
    public void 유저찾기(){
        Users a = new Users().setUserId(this.testUserId).setPassword("hello!@345");

       Optional<Users> result = usersRepository.findByUserId(a.getUserId());
//               .filter(returnUser-> returnUser.getPassword().equals(a.getPassword()));
        System.out.println(result);

        System.out.println(result.filter((user)->{
            System.out.println(user);
            return true;
        }));

    }


    @Test
    public void 유져타입_가져오기(){
        UserType facebook = userTypeRepository.findByTypeName("facebook").get();
        System.out.println(facebook);


    }
}
