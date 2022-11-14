package com.info;

import java.util.List;

public class PlayerScoreInfos {
    private List<PlayerScoreInfo> scores;

    public PlayerScoreInfos(List<PlayerScoreInfo> scores) {
        this.scores = scores;
    }

    public List<PlayerScoreInfo> getScores() {
        return scores;
    }

    public void setScores(List<PlayerScoreInfo> scores) {
        this.scores = scores;
    }
}
