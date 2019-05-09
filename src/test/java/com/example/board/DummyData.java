//package com.example.board;
//
//import com.example.board.post.PostRepository;
//import com.example.board.Users.UsersRepository;
//import com.example.board.dao.UserRolesRepository;
//import com.example.board.post.Post;
//import com.example.board.domain.UserRoles;
//import com.example.board.Users.Users;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
////@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class DummyData {
//
//    @Autowired
//    UsersRepository userRepository;
//
//    @Autowired
//    UserRolesRepository rolesRepository;
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Test
//    public void aSetUser(){
//        Users users1 = new Users("giraffeb","giraffeb");
//        userRepository.save(users1);
//        System.out.println(userRepository.findUserByUsername("giraffeb"));
//    }
//
//
//    @Test
//    public void bSetUserRole(){
//        Users user1 = userRepository.findUserByUsername("giraffeb").get();
//        UserRoles roles = new UserRoles(user1, "ADMIN");
//        rolesRepository.save(roles);
//    }
//
//    @Test
//    public void cSetPostTest(){
//        Users user = userRepository.findUserByUsername("giraffeb").get();
//        System.out.println(user);
//        Post newPost = new Post(user, "this is post", "this is content");
//        postRepository.save(newPost);
//
//        for(int i=0;i<30;i++){
//            postRepository.save(new Post(user, "this is post"+i, "this is content"+i));
//
//        }
//
//    }
//
//
//
//
//}
