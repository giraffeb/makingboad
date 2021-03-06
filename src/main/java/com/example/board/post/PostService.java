package com.example.board.post;

import com.example.board.Users.UsersRepository;
import com.example.board.Users.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private static Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsersRepository userRepository;

    public List<Post> getPostAll(Map<String, Object> parmas){

        if(parmas.get("currentPage") == null){
            parmas.put("currentPage", 0);
        }


        int currentPage = Integer.parseInt(parmas.get("currentPage").toString());
        int pageSize = Integer.parseInt(parmas.get("pageSize").toString());

        List<Post> list = postRepository.findAll(PageRequest.of(currentPage,pageSize))
                                        .getContent();

        return list;
    }

    public Post getPost(Map<String, Object> params,
                        HttpSession session) throws Exception{

        if(params.get("post_id") == null){
            return null;
        }
        int postId = Integer.parseInt((String) params.get("post_id"));
        Post post = postRepository.findById(postId).get();

        String username = (String) session.getAttribute("username");

        if(!post.getWriter().getUsername().equals(username)){
            post.setViewCount(post.getViewCount()+1);
            postRepository.save(post);
        }


        return post;

    }

    public Post getPostRest(Integer postId,
                        HttpSession session) throws Exception{

        if(postId == null){
            return null;
        }

        Post post = postRepository.findById(postId).get();
        String username = (String) session.getAttribute("username");

        if(!post.getWriter().getUsername().equals(username)){
            post.setViewCount(post.getViewCount()+1);
            postRepository.save(post);
        }

        return post;

    }


    public void updatePost(Map<String, Object> params,
                             HttpSession session) throws Exception{


        String username = (String) session.getAttribute("username");

        logger.debug("USERNAME -> "+username);
        logger.debug("SESSION -> "+session);
        logger.debug("PARAMS ->"+params);
        Users user = userRepository.findUserByUsername(username).get();


        String title = (String)params.get("title");
        String content = (String)params.get("content");
        int post_id = Integer.parseInt((String)params.get("post_id"));

        Post post = postRepository.findById(post_id).get();
        post.setTitle(title);
        post.setContent(content);

        postRepository.saveAndFlush(post);


    }

    public void deletePost(Map<String,Object> params, HttpSession session) {

        int post_id = Integer.parseInt((String)params.get("post_id"));

        Post thisPost = postRepository.findById(post_id).get();
        logger.debug(thisPost.toString());
        String username = (String)session.getAttribute("username");

        logger.debug("#SESSION NAME->"+username);
        if(thisPost.getWriter().getUsername().equals(username)){
            postRepository.deleteById(post_id);

        }
    }


    public void writePost(Map<String,Object> params,
                          HttpSession session,
                          HttpServletRequest request) {

        String title = (String)params.get("title");
        String content = (String)params.get("content");

        //TODO: session 기반에서 jwt 사용 기반으로 변경되었으므로, 이것도 그에 맞게 변경합시다 어떻게 처리해야 될 지는 생각해보고.


        System.out.println("#SESSION userid->"+session.getAttribute("userid"));
        System.out.println("#SESSION username->"+session.getAttribute("username"));
        Users user = userRepository.findByUserId((String)session.getAttribute("userid"))
                .get();


        Post newPost = new Post(user, title, content);
        postRepository.save(newPost);
    }

    public int getMaxPageNumber(Map<String,Object> params){
        int pageSize = (Integer)params.get("pageSize");
        pageSize += 1; //pageRequest는 9일경우 0-9 이기때문.
        int totalCnt = postRepository.getMaxPageNumber();

        int result = totalCnt/pageSize;
        int others = totalCnt%pageSize;
        if(others > 0){
            result += 1;
        }
        return result;

    }
}
