package com.delegate.game;

import com.exception.BusinessException;
import com.service.game.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("gameDelegate")
public class GameDelegate implements IGameDelegate {

    @Autowired
    private IGameService gameService;

    @Override
    public void startGame() throws BusinessException {
        gameService.startGame();
    }
}
