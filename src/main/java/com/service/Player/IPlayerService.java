package com.service.player;

import com.entity.Player;
import com.exception.BusinessException;

public interface IPlayerService {
    Player createPlayer(Player player) throws BusinessException;
}
