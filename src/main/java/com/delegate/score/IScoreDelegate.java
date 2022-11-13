package com.delegate.score;

import com.exception.BusinessException;
import com.exception.NotFoundException;
import com.info.PlayerScoreInfo;

import java.util.List;

public interface IScoreDelegate {
    List<PlayerScoreInfo> getScores() throws NotFoundException, BusinessException;
}
