package com.service.Player;

import com.constant.MessageConstant;
import com.dao.IPlayerDao;
import com.entity.Player;
import com.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("playerService")
public class PlayerService implements IPlayerService {

    @Autowired
    private IPlayerDao playerDao;

    @Override
    public Player createPlayer(Player player) throws BusinessException {
        Player playerObject = null;
        synchronized (this) {
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
}
