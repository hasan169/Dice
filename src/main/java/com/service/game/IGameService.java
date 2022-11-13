package com.service.game;

import com.entity.Player;
import com.exception.BusinessException;

public interface IGameService {

   Player registerNewPlayer(Player player) throws BusinessException;

   void startGame() throws BusinessException;
}
