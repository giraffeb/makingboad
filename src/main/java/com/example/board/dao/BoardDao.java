package com.example.board.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
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

    public Map<String, Object> getPost(Map<String, Object> params){
        String postId = (String)params.get("post_id");
        Map<String, Object> post = jdbcTemplate.queryForMap("SELECT * FROM post WHERE id_no = ?", postId);

        return post;
    }


    public boolean writePost(Map<String, Object> params){

        int TEMP_CATEGORY = 1; //임시로 고정 값으로 처리한다.

        jdbcTemplate.update(
                "INSERT INTO post(title, content, writer, category) " +
                "VALUES(?, ?, ?, ?)",
                params.get("title"),
                params.get("content"),
                params.get("writer"),
                TEMP_CATEGORY);
        //성공, 실패를 나눠줄 것
        return true;
    }

    public boolean updatePost(Map<String, Object> params){

        jdbcTemplate.update("UPDATE post " +
                "SET title = ? ," +
                "content = ?" +
                "WHERE id_no = ?",
                params.get("title"),
                params.get("content"),
                params.get("post_id"));

        return true;
    }

    public boolean loginCheck(Map<String, Object> params){
        boolean result = false;

        String user_id = (String)params.get("id");
        String user_pw = (String)params.get("pw");

        Map<String, Object> row = jdbcTemplate.queryForMap("SELECT COUNT(*) as cnt " +
                "FROM user " +
                "WHERE user_id = ? AND user_pw = ?", user_id, user_pw);
        System.out.println(row);

        Long cnt = (Long)row.get("cnt");

        if(cnt == 0){
            result = false;
        }else{
            result = true;
        }

        System.out.println("RESULT : "+result);

        return result;
    }
}
