package com.delegate.player;

import com.exception.BusinessException;
import com.info.PlayerInfo;

public interface IPlayerDelegate {

    PlayerInfo createNewPlayer(PlayerInfo playerInfo) throws BusinessException;
}
