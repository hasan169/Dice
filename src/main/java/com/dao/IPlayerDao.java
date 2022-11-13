package com.dao;

import com.entity.Player;

import java.util.List;

public interface IPlayerDao {

   List<Player> getAllPlayers();

   void addNewPlayer(Player player);

   void updatePlayerScore(String id, int score);

   Integer getPlayerScore(String id);

   void clear();
}
