package com.wechat.mapper;

import com.wechat.pojo.Score;

import java.util.List;

public interface SelectScoreMapper {

    public List<Score> queryScore(Score score);

    public List<Score> queryYear(String userId);
}
