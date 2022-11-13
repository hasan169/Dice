package service.game;

import com.constant.MessageConstant;
import com.dao.PlayerDao;
import com.entity.Player;
import com.exception.BusinessException;
import com.service.game.GameService;
import com.service.game.SpecialDiceRoll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestGameService {

    @Mock
    SpecialDiceRoll diceRoll;

    @Test
    public void testFirstPlayerShouldWin() {
        int counter = 0;
        PlayerDao playerDao = new PlayerDao();
        GameService gameService = new GameService(playerDao, diceRoll);
        playerDao.addNewPlayer(new Player("A", 22));
        playerDao.addNewPlayer(new Player("B", 33));
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(3).when(diceRoll).rollDice(counter++);
        doReturn(2).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(1).when(diceRoll).rollDice(counter++);
        doReturn(1).when(diceRoll).rollDice(counter++);
        gameService.play();
        assertEquals((int)playerDao.getPlayerScore("1"), 25);
        assertNull(playerDao.getPlayerScore("2"));
    }

    @Test
    public void testSecondPlayerShouldWin() {
        int counter = 0;
        PlayerDao playerDao = new PlayerDao();
        GameService gameService = new GameService(playerDao, diceRoll);
        playerDao.addNewPlayer(new Player("A", 22));
        playerDao.addNewPlayer(new Player("B", 33));
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);

        gameService.play();
        assertEquals((int)playerDao.getPlayerScore("1"), 10);
        assertEquals((int)playerDao.getPlayerScore("2"), 26);
    }

    @Test
    public void testThirdPlayerShouldWin() {
        int counter = 0;
        PlayerDao playerDao = new PlayerDao();
        GameService gameService = new GameService(playerDao, diceRoll);
        playerDao.addNewPlayer(new Player("A", 22));
        playerDao.addNewPlayer(new Player("B", 33));
        playerDao.addNewPlayer(new Player("C", 43));
        doReturn(3).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(2).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(1).when(diceRoll).rollDice(counter++);
        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(3).when(diceRoll).rollDice(counter++);
        doReturn(1).when(diceRoll).rollDice(counter++);
        doReturn(1).when(diceRoll).rollDice(counter++);
        doReturn(1).when(diceRoll).rollDice(counter++);
        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(2).when(diceRoll).rollDice(counter++);

        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(6).when(diceRoll).rollDice(counter++);
        doReturn(1).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(4).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        doReturn(5).when(diceRoll).rollDice(counter++);
        gameService.play();
        assertEquals((int)playerDao.getPlayerScore("1"), -14);
        assertEquals((int)playerDao.getPlayerScore("2"), 17);
        assertEquals((int)playerDao.getPlayerScore("3"), 28);
    }

    @Test
    public void testRegisterNewPlayer() throws BusinessException {
        PlayerDao playerDao = new PlayerDao();
        GameService gameService = new GameService(playerDao, diceRoll);
        registerFourPlayer(gameService);
        List<Player> playerList = playerDao.getAllPlayers();
        assertEquals(playerList.size(), 4);
    }

    @Test
    public void testRegisterNewPlayerShouldThrowPlayerExceedException() throws BusinessException {
        PlayerDao playerDao = new PlayerDao();
        GameService gameService = new GameService(playerDao, diceRoll);
        registerFourPlayer(gameService);
        try {
            gameService.registerNewPlayer(new Player("E", 77));
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), MessageConstant.PLAYERS_NUMBER_EXCEED_MESSAGE);
        }
    }

    @Test
    public void testStartGame() throws BusinessException {
        PlayerDao playerDao = new PlayerDao();
        GameService gameService = spy(new GameService(playerDao, diceRoll));
        registerFourPlayer(gameService);
        doNothing().when(gameService).createNewThreadForGame();
        gameService.startGame();
        verify(gameService).createNewThreadForGame();
    }

    @Test
    public void testStartGameShouldThrowMinimumPlayerException() throws BusinessException {
        PlayerDao playerDao = new PlayerDao();
        GameService gameService = spy(new GameService(playerDao, diceRoll));
        Player player1 = new Player("A", 22);
        gameService.registerNewPlayer(player1);
        try {
            gameService.startGame();
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), MessageConstant.MINIMUM_PLAYERS_NUMBER_MESSAGE);
        }

        verify(gameService, never()).play();
    }


    private void registerFourPlayer(GameService gameService) throws BusinessException {
        Player player1 = new Player("A", 22);
        gameService.registerNewPlayer(player1);
        Player player2 = new Player("B", 22);
        gameService.registerNewPlayer(player2);
        Player player3 = new Player("C", 22);
        gameService.registerNewPlayer(player3);
        Player player4 = new Player("D", 22);
        gameService.registerNewPlayer(player4);
    }
}
