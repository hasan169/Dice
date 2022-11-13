package com.controller.game;

import com.constant.MessageConstant;
import com.controller.player.PlayerController;
import com.delegate.game.IGameDelegate;
import com.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-start")
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private IGameDelegate gameDelegate;

    @GetMapping
    public ResponseEntity startGame() {
        try {
            logger.info("Received start game request ");
            gameDelegate.startGame();
        } catch (BusinessException e) {
            logger.error("Business error while starting game ", e);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Exception while starting game ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageConstant.SERVER_ERROR_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.OK).body(MessageConstant.GAME_START_MESSAGE);
    }
}
