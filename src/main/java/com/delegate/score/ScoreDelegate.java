package com.delegate.score;

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

    @Autowired
    private IPlayerService playerService;

    @Override
    public List<PlayerScoreInfo> getScores() throws NotFoundException {
        List<PlayerScoreInfo> playerScoreInfoList = new ArrayList<>();
        List<Player> players = playerService.getAllPlayers();
        if (players != null) {
            for (Player player: players) {
                playerScoreInfoList.add(new PlayerScoreInfo(player.getId(), player.getName(), player.getAge(),
                        player.getScore()));
            }
        }
        return playerScoreInfoList;
    }
}
