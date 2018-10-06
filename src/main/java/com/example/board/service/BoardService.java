package com.example.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.board.dao.BoardDao;

import java.util.List;
import java.util.Map;

@Component
public class BoardService {

    private final BoardDao boardDao;

    @Autowired
    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public List<Map<String,Object>> getList(){

        return boardDao.getList();
    }

    public Map<String, Object> getPost(Map<String, Object> params){

        return boardDao.getPost(params);
    }

    public boolean writePost(Map<String, Object> params){
        //TO-DO : 임시유저처리
        params.put("writer", 1);

        return boardDao.writePost(params);
    }

    public boolean updatePost(Map<String,Object> params){

        return boardDao.updatePost(params);
    }

    public boolean loginCheck(Map<String, Object> params){

        return boardDao.loginCheck(params);
    }
}
