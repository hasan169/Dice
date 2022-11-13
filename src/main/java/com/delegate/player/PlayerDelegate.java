package com.delegate.player;

import com.constant.MessageConstant;
import com.entity.Player;
import com.exception.BusinessException;

import com.info.PlayerInfo;
import com.service.Player.IPlayerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("playerDelegate")
public class PlayerDelegate implements IPlayerDelegate {

    @Autowired
    private IPlayerService playerService;

    @Override
    public PlayerInfo createNewPlayer(PlayerInfo playerInfo) throws BusinessException {
        validateRequest(playerInfo);
        Player player = playerService.createPlayer(new Player(playerInfo.getName(), playerInfo.getAge()));
        return new PlayerInfo(player.getId(), player.getName(), player.getAge());
    }

    private void validateRequest(PlayerInfo playerInfo) throws BusinessException {
        if (StringUtils.isBlank(playerInfo.getName())) {
            throw new BusinessException(MessageConstant.EMPTY_NAME_MESSAGE);
        }

        if (playerInfo.getAge() <= 0) {
            throw new BusinessException(MessageConstant.NEGATIVE_AGE_MESSAGE);
        }
    }
}
