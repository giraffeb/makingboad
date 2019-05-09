package com.example.board.myauth;


import com.example.board.Users.Users;
import com.example.board.Users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    private UsersRepository usersRepository;

    public AuthService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean checkLogin(Users targetUser){
        Optional<Users> resultUser = usersRepository
                                .findUserByUsername(targetUser.getUsername())
                                .filter(returnUser-> returnUser.getPassword().equals(targetUser.getPassword()));

        logger.debug("loginCheck -> "+resultUser.toString());

        return resultUser.isPresent();
    }
}
