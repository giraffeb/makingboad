package com.example.board;

import com.example.board.Users.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class BoardApplicationTests {

    @Autowired
    UserRepository userRepository;
//
//    @Autowired
//    UserRolesRepository userRolesRepository;


//    @AuthInterceptor
//    public void addUserTest(){
//        Users newUser = new Users("giraffeb",
//                "giraffeb",
//                true);
//        Users resultUser = userRepository.save(newUser);
//
//        assertThat(resultUser.getUsername()).isEqualTo("giraffeb");
//        assertThat(resultUser.getPassword()).isEqualTo("giraffeb");
//        System.out.println(resultUser);
//    }


//    @AuthInterceptor
//    public void addUserRolesTest(){
//        Users newUser = new Users("giraffeb", "giraffeb", true);
//        UserRoles userRole = new UserRoles(newUser, "ADMIN");
//
//        UserRoles resultUserRole = userRolesRepository.save(userRole);
//        System.out.println(resultUserRole);
//        assertThat(resultUserRole.getRole()).isEqualTo("ADMIN");
//
//    }
}
