package service.game;

import com.dao.PlayerDao;
import com.entity.Player;
import com.service.game.GameService;
import com.service.game.SpecialDiceRoll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
}
