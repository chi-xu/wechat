package com.wechat.service;

import com.wechat.mapper.SelectScoreMapper;
import com.wechat.pojo.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectScoreServiceImpl implements SelectScoreService{

    @Autowired
    private SelectScoreMapper selectScoreMapper;

    @Override
    public List<Score> queryScore(Score score) {

        return this.selectScoreMapper.queryScore(score);
    }

    @Override
    public List<Score> queryYear(String userId) {
        System.out.println("SSSSSSSSSSSSSSSSSS"+this.selectScoreMapper.queryYear(userId).size());
        return this.selectScoreMapper.queryYear(userId);
    }
}
