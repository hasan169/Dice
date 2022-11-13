package com.service.Player;

import com.entity.Player;
import com.exception.BusinessException;

public interface IPlayerService {
    Player createPlayer(Player player) throws BusinessException;
}
