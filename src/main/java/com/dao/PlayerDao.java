package com.dao;

import com.entity.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("PlayerDao")
public class PlayerDao implements IPlayerDao {

    private final List<Player> playerList = new ArrayList<>();

    @Override
    public List<Player> getAllPlayers() {
        return playerList;
    }

    @Override
    public void addNewPlayer(Player player) {
        playerList.add(player);
    }


}
