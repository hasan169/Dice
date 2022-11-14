package com.controller.score;

import com.constant.MessageConstant;
import com.delegate.score.IScoreDelegate;
import com.exception.NotFoundException;
import com.info.PlayerScoreInfo;
import com.info.PlayerScoreInfos;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @Autowired
    private IScoreDelegate scoreDelegate;

    @GetMapping
    @ApiOperation(value = "Getting player scores", response = PlayerScoreInfos.class)
    public ResponseEntity getScores() {
        logger.info("Received get scores request");
        List<PlayerScoreInfo> playerScoreInfoList;
        try {
            playerScoreInfoList =  scoreDelegate.getScores();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            logger.error("Exception while getting scores ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageConstant.SERVER_ERROR_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new PlayerScoreInfos(playerScoreInfoList));
    }
}
