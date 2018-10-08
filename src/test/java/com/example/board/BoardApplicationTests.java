package com.example.board;

import com.example.board.dao.BoardDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardApplicationTests {

    @Test
    public void getString(){
        String imgfile = "hello.jpg";
        System.out.println(imgfile.substring(imgfile.length()-4, imgfile.length()));

    }

}
