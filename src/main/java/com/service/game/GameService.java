package com.service.game;

import com.constant.MessageConstant;
import com.dao.IPlayerDao;
import com.entity.Player;
import com.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("gameService")
public class GameService implements IGameService {

    private volatile boolean isGameRunning = false;
    private volatile boolean canNewRoundStart = true;

    @Autowired
    private IPlayerDao playerDao;

    @Autowired
    private IDiceRoll diceRoll;

    @Override
    public Player registerNewPlayer(Player player) throws BusinessException {
        Player playerObject;
        synchronized (this) {
            if (canNewRoundStart) {
                playerDao.clear();
                canNewRoundStart = false;
            }
            if (isGameRunning) {
                throw new BusinessException(MessageConstant.NEW_PLAYER_GAME_ALREADY_RUNNING_MESSAGE);
            }
            List<Player> playerList = playerDao.getAllPlayers();
            if (playerList.size() < 4) {
                playerObject = new Player(player.getName(), player.getAge());
                playerDao.addNewPlayer(playerObject);
            } else {
                throw new BusinessException(MessageConstant.PLAYERS_NUMBER_EXCEED_MESSAGE);
            }
        }
        return playerObject;
    }

    @Override
    public void startGame() throws BusinessException {
        synchronized (this) {
            if (isGameRunning) {
                throw new BusinessException(MessageConstant.GAME_ALREADY_RUNNING_MESSAGE);
            }
            List<Player> playerList = playerDao.getAllPlayers();
            if (playerList.size() < 2) {
                throw new BusinessException(MessageConstant.MINIMUM_PLAYERS_NUMBER_MESSAGE);
            }
            isGameRunning = true;

            new Thread(this::play).start();
        }
    }

    private void play() {
        try {
            boolean winnerFound = false;
            List<Player> playerList = playerDao.getAllPlayers();
            while (!winnerFound) {
                for (Player player: playerList) {
                    Integer initialScore = playerDao.getPlayerScore(player.getId());
                    Integer totalScore = initialScore;
                    int newScore = diceRoll.rollDice();
                    printScore(player, totalScore, newScore);
                    int totalSix = 0;
                    if (newScore == 6) {
                        totalScore = totalScore == null ? 0 : totalScore;
                        while (newScore == 6) {
                            totalSix++;
                            if (totalScore + newScore >= 25) {
                                winnerFound = true;
                                break;
                            }
                            totalScore = totalScore + newScore;
                            printScore(player, totalScore, newScore);
                            newScore = diceRoll.rollDice();
                        }
                        if (initialScore == null && totalSix == 1 && newScore == 4) {
                            continue;
                        }
                    }
                    if (totalScore != null) {
                        totalScore = totalScore + (newScore == 4 ? -4 : newScore);
                        printScore(player, totalScore, newScore);
                        playerDao.updatePlayerScore(player.getId(), totalScore);

                        if (totalScore >= 25) {
                            winnerFound = true;
                            break;
                        }
                    } else {
                        printScore(player, totalScore, newScore);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isGameRunning = false;
            canNewRoundStart = true;
        }
    }

    private void printScore(Player player, Integer totalScore, int diceValue) {
        System.out.println(String.format("Player name: %s, Total Score: %s, Current Value of Dice: %s",
                player.getName(), totalScore, diceValue));
    }
}
