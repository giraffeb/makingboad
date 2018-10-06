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

	@Autowired
	public BoardDao dao;


	@Test
	public void contextLoads() {

		Map<String, Object> params = new HashMap<>();
		params.put("post_id", 1);

		dao.getPost(params);
	}

	@Test
	public void getList(){

		List<Map<String, Object>> result = dao.getList();
		System.out.println( result );

	}


	@Test
	public void getPostparams(){



	}
}
