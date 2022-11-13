package com.controller.score;

import com.constant.MessageConstant;
import com.delegate.score.IScoreDelegate;
import com.info.PlayerScoreInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private IScoreDelegate scoreDelegate;

    @GetMapping
    public ResponseEntity getScores() {
        List<PlayerScoreInfo> playerScoreInfoList;
        try {
            playerScoreInfoList =  scoreDelegate.getScores();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageConstant.SERVER_ERROR_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerScoreInfoList);
    }
}
