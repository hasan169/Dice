package com.service.game;

import com.config.ConfigProperties;
import com.constant.MessageConstant;
import com.constant.Properties;
import com.dao.IPlayerDao;
import com.entity.Player;
import com.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("gameService")
public class GameService implements IGameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private volatile boolean isGameRunning = false;
    private volatile boolean canNewRoundStart = true;

    private IPlayerDao playerDao;
    private IDiceRoll diceRoll;
    private ConfigProperties configProperties;

    @Autowired
    public GameService(IPlayerDao playerDao, IDiceRoll diceRoll, ConfigProperties configProperties) {
        this.playerDao = playerDao;
        this.diceRoll = diceRoll;
        this.configProperties = configProperties;
    }

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
            createNewThreadForGame();
        }
    }

    public void createNewThreadForGame() {
        new Thread(this::play).start();
    }

    public void play() {
        try {
            int maximumScore = Integer.parseInt(configProperties.getConfigValue(Properties.DICE_MAXIMUM_SCORE));
            logger.info("Maximum Score {}", maximumScore);
            int dictRollCounter = 0;
            boolean winnerFound = false;
            List<Player> playerList = playerDao.getAllPlayers();
            while (!winnerFound) {
                for (Player player: playerList) {
                    Integer initialScore = playerDao.getPlayerScore(player.getId());
                    Integer totalScore = initialScore;
                    int newScore = diceRoll.rollDice(dictRollCounter++);
                    int totalSix = 0;
                    if (newScore == 6) {
                        totalScore = totalScore == null ? 0 : totalScore;
                        while (newScore == 6) {
                            totalSix++;
                            if (totalScore + newScore >= maximumScore) {
                                System.out.println("Player won " + player);
                                logger.info("Player won {}", player);
                                winnerFound = true;
                                break;
                            }
                            if (!(initialScore == null && totalSix == 1)) {
                                totalScore = totalScore + newScore;
                            }
                            printScore(player, totalScore, newScore);
                            newScore = diceRoll.rollDice(dictRollCounter++);
                        }
                        // Since the player got 4 after hitting the first 6, he will wait for his next turn
                        if (initialScore == null && totalSix == 1 && newScore == 4) {
                            printScore(player, totalScore, -4);
                            continue;
                        }
                    }
                    if (totalScore != null) {
                        totalScore = totalScore + (newScore == 4 ? -4 : newScore);
                        printScore(player, totalScore, newScore);
                        playerDao.updatePlayerScore(player.getId(), totalScore);

                        if (totalScore >= maximumScore) {
                            System.out.println("Player won " + player);
                            logger.info("Player won {}", player);
                            winnerFound = true;
                            break;
                        }
                    } else {
                        printScore(player, totalScore, newScore);
                    }
                }

            }
        } catch (Exception e) {
            logger.error("Error while running game ", e);
            e.printStackTrace();
        } finally {
            isGameRunning = false;
            canNewRoundStart = true;
        }
    }

    private void printScore(Player player, Integer totalScore, int diceValue) {
        logger.info("Player name: {}, Total Score: {}, Current Value of Dice: {}", player.getName(), totalScore, diceValue);
        System.out.println(String.format("Player name: %s, Total Score: %s, Current Value of Dice: %s",
                player.getName(), totalScore, diceValue));
    }
}
