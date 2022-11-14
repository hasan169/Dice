package com.service.player;

import com.entity.Player;
import com.exception.BusinessException;

import java.util.List;

public interface IPlayerService {

    Player createPlayer(Player player) throws BusinessException;

    List<Player> getAllPlayers();
}
