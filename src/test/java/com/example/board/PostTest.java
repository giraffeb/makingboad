package com.example.board;

import com.example.board.post.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
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

    private MockHttpSession session;

    @Autowired
    private WebApplicationContext context;
    private int lastPostId;


    @Before
    public void setUp() {

        session = new MockHttpSession();
        session.setAttribute("username", "giraffeb");
        lastPostId = postRepository.getLastPostByUserId(1).getPost_id();

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
    public void 게시물_작성하기() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "This is test title.");
        params.add("content", "This is test content.");


        mvc.perform(get("/post")
                .params(params)
        ).andExpect(status().is(302))
                .andDo(print());
    }


    @Test
    public void 게시물_상세_가져오기() throws Exception {

        mvc.perform(get("/post")
                .param("post_id", String.valueOf(lastPostId))
                .session(session)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 게시물_리스트_가져오기() throws Exception {
        mvc.perform(get("/")
                    .session(session)
                ).andExpect(status().isOk())
                .andDo(print());
    }



    @Test
    public void 게시물_수정하기() throws Exception {
        mvc.perform(post("/updatePost")
                .session(session)
                .param("post_id", String.valueOf(lastPostId))
                .param("title", "change test")
                .param("content", "contetnt_change")
        ).andExpect(status().is(302))
                .andDo(print());
    }


    @Test
    public void 게시물_삭제하기() throws Exception {
//        가장 최근 게시물 삭제하기.
//        내가 작성한 글의 아이디를 가져올 수 있어야 한다. -> 현재 불가능


        mvc.perform(post("/deletePost")
                .session(session)
                .param("post_id", String.valueOf(lastPostId))
        ).andExpect(status().is(302))
                .andDo(print());

    }
}
