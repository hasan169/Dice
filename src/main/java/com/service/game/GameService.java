package com.service.game;

import com.constant.MessageConstant;
import com.dao.IPlayerDao;
import com.entity.Player;
import com.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("gameService")
public class GameService implements IGameService {

    private boolean isGameRunning = false;

    @Autowired
    private IPlayerDao playerDao;

    @Override
    public Player registerNewPlayer(Player player) throws BusinessException {
        Player playerObject;
        synchronized (this) {
            if (isGameRunning) {
                throw new BusinessException(MessageConstant.NEW_PLAYER_GAME_ALREADY_RUNNING_MESSAGE);
            }
            List<Player> playerList = playerDao.getAllPlayers();
            if (playerList.size() < 4) {
                String id = String.valueOf(playerList.size() + 1);
                playerObject = new Player(id, player.getName(), player.getAge());
                playerDao.addNewPlayer(playerObject);
            } else {
                throw new BusinessException(MessageConstant.PLAYERS_NUMBER_EXCEED_MESSAGE);
            }
        }
        return playerObject;
    }

    @Override
    public void startGame() throws BusinessException {
        synchronized (this) {
            if (isGameRunning) {
                throw new BusinessException(MessageConstant.GAME_ALREADY_RUNNING_MESSAGE);
            }
            List<Player> playerList = playerDao.getAllPlayers();
            if (playerList.size() < 2) {
                throw new BusinessException(MessageConstant.MINIMUM_PLAYERS_NUMBER_MESSAGE);
            }
            isGameRunning = true;
        }
    }


}
