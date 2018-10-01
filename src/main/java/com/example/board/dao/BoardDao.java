package com.example.board.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BoardDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BoardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getList(){
        List<Map<String,Object>> result = jdbcTemplate.queryForList("SELECT * FROM post");

        return result;
    }

}
