package com.controller.game;

import com.constant.MessageConstant;
import com.delegate.game.IGameDelegate;
import com.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-start")
public class GameController {

    @Autowired
    private IGameDelegate gameDelegate;

    @GetMapping
    public ResponseEntity startGame() {
        try {
            gameDelegate.startGame();
        } catch (BusinessException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageConstant.SERVER_ERROR_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.OK).body(MessageConstant.GAME_START_MESSAGE);
    }
}
