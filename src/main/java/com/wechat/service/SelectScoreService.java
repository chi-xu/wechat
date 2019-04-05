package com.wechat.service;

import com.wechat.pojo.Score;

import java.util.List;

public interface SelectScoreService {

    public List<Score> queryScore(Score score);

    public List<Score> queryYear(String userId);
}
