package service.game;

import com.constant.MessageConstant;
import com.delegate.score.ScoreDelegate;
import com.entity.Player;
import com.exception.BusinessException;
import com.exception.NotFoundException;
import com.info.PlayerScoreInfo;
import com.service.player.IPlayerService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestScoreDelegate {

    @Mock
    IPlayerService playerService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testGetScoresShouldThrowNotFoundException() throws NotFoundException {
        doReturn(new ArrayList<>()).when(playerService).getAllPlayers();
        expectedEx.expect(NotFoundException.class);
        expectedEx.expectMessage(MessageConstant.NO_SCORE_FOUND_MESSAGE);
        ScoreDelegate scoreDelegate = new ScoreDelegate(playerService);
        scoreDelegate.getScores();
    }

    @Test
    public void testGetScores() throws NotFoundException {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("1","A", 22, 55));
        playerList.add(new Player("2","B", 33, 66));
        doReturn(playerList).when(playerService).getAllPlayers();
        ScoreDelegate scoreDelegate = new ScoreDelegate(playerService);
        List<PlayerScoreInfo> playerScoreInfoList = scoreDelegate.getScores();
        for (int index = 0; index < playerList.size(); index++) {
            assertEquals(playerList.get(index).getName(), playerScoreInfoList.get(index).getName());
            assertEquals(playerList.get(index).getAge(), playerScoreInfoList.get(index).getAge());
            assertEquals(playerList.get(index).getId(), playerScoreInfoList.get(index).getId());
            assertEquals(playerList.get(index).getScore(), playerScoreInfoList.get(index).getScore());
        }
    }
}
