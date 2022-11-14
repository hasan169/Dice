package com.service.player;

import com.constant.MessageConstant;
import com.dao.IPlayerDao;
import com.entity.Player;
import com.exception.BusinessException;
import com.exception.NotFoundException;
import com.service.game.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("playerService")
public class PlayerService implements IPlayerService {

    @Autowired
    private IGameService gameService;

    @Autowired
    IPlayerDao playerDao;

    @Override
    public Player createPlayer(Player player) throws BusinessException {
        return gameService.registerNewPlayer(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerDao.getAllPlayers();
    }
}
