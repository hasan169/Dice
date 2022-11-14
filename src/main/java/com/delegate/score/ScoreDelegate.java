package com.delegate.score;

import com.constant.MessageConstant;
import com.entity.Player;
import com.exception.NotFoundException;
import com.info.PlayerScoreInfo;
import com.service.player.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("scoreDelegate")
public class ScoreDelegate implements IScoreDelegate {

    private IPlayerService playerService;

    @Autowired
    public ScoreDelegate(IPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public List<PlayerScoreInfo> getScores() throws NotFoundException {
        List<PlayerScoreInfo> playerScoreInfoList = new ArrayList<>();
        List<Player> players = playerService.getAllPlayers();
        if (players == null || players.size() == 0) throw new NotFoundException(MessageConstant.NO_SCORE_FOUND_MESSAGE);
        for (Player player: players) {
            playerScoreInfoList.add(new PlayerScoreInfo(player.getId(), player.getName(), player.getAge(),
                    player.getScore()));
        }
        return playerScoreInfoList;
    }
}
