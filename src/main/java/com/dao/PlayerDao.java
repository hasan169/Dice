package com.dao;

import com.entity.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component("PlayerDao")
public class PlayerDao implements IPlayerDao {

    private int playerId = 0;
    private final Map<String, Player> playerMap = new ConcurrentHashMap();

    @Override
    public List<Player> getAllPlayers() {
        return new ArrayList<>(playerMap.values());
    }

    @Override
    public void addNewPlayer(Player player) {
        String id = String.valueOf(++playerId);
        player.setId(id);
        playerMap.put(id, player);
    }

    @Override
    public void updatePlayerScore(String id, int score) {
        playerMap.get(id).setScore(score);
    }

    @Override
    public Integer getPlayerScore(String id) {
        return playerMap.get(id).getScore();
    }

    @Override
    public void clear() {
        playerMap.clear();
    }
}
