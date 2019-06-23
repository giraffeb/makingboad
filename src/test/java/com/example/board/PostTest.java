package com.example.board;

import com.example.board.Users.Users;
import com.example.board.Users.UsersRepository;
import com.example.board.myauth.AuthService;
import com.example.board.myauth.OauthService;
import com.example.board.post.Post;
import com.example.board.post.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthService authService;

    @Autowired
    OauthService oauthService;

    @Autowired
    UsersRepository usersRepository;

    private MockHttpServletResponse response;
    private MockHttpSession session;

    @Autowired
    private WebApplicationContext context;
    private int lastPostId;


    @Before
    public void setUp() {

        response = new MockHttpServletResponse();
        session = new MockHttpSession();

        Users tempUser = usersRepository.findByUserId("hello").get();
        authService.createAuth(response, session, tempUser);
        Post tempPost = postRepository.getLastPostByUserNo(tempUser.getUserNo());
        System.out.println(tempPost);
        lastPostId = tempPost.getPost_id();


    }

//    @Before
//    public void preLogin() throws Exception {
//        session = new MockHttpSession();
//        session.setAttribute("username", "giraffeb");
//    }

    @Test
    public void hello() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void 게시물_작성하기() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "This is test title.");
        params.add("content", "This is test content.");


        mvc.perform(post("/writePost")
                .params(params)
                .cookie(response.getCookie("mymymy"))
                .session(session)
        ).andExpect(status().is(302))
                .andDo(print());
    }


    @Test
    public void 게시물_상세_가져오기() throws Exception {

        mvc.perform(get("/post")
                .param("post_id", String.valueOf(lastPostId))
                .cookie(response.getCookie("mymymy"))
                .session(session)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 게시물_리스트_가져오기() throws Exception {
        mvc.perform(get("/")
                .cookie(response.getCookie("mymymy"))
                .session(session)
                ).andExpect(status().isOk())
                .andDo(print());
    }



    @Test
    @Transactional
    public void 게시물_수정하기() throws Exception {
        mvc.perform(post("/updatePost")
                .cookie(response.getCookie("mymymy"))
                .session(session)
                .param("post_id", String.valueOf(lastPostId))
                .param("title", "change test")
                .param("content", "contetnt_change")
        ).andExpect(status().is(302))
                .andDo(print());
    }


    @Test
    @Transactional
    public void 게시물_삭제하기() throws Exception {
//        가장 최근 게시물 삭제하기.
//        내가 작성한 글의 아이디를 가져올 수 있어야 한다. -> 현재 불가능


        mvc.perform(post("/deletePost")
                .cookie(response.getCookie("mymymy"))
                .session(session)
                .param("post_id", String.valueOf(lastPostId))
        ).andExpect(status().is(302))
                .andDo(print());

    }
}
