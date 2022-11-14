package delegate;

import com.constant.MessageConstant;
import com.delegate.player.PlayerDelegate;
import com.entity.Player;
import com.exception.BusinessException;
import com.info.PlayerInfo;
import com.service.player.IPlayerService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestPlayerDelegate {

    @Mock
    IPlayerService playerService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testCreateNewPlayerShouldThrowEmptyNameMessage() throws BusinessException {
        PlayerDelegate playerDelegate = new PlayerDelegate(playerService);
        expectedEx.expect(BusinessException.class);
        expectedEx.expectMessage(MessageConstant.EMPTY_NAME_MESSAGE);
        playerDelegate.createNewPlayer(new PlayerInfo("", 3));
    }

    @Test
    public void testCreateNewPlayerShouldThrowNegativeAgeMessage() throws BusinessException {
        PlayerDelegate playerDelegate = new PlayerDelegate(playerService);
        expectedEx.expect(BusinessException.class);
        expectedEx.expectMessage(MessageConstant.NEGATIVE_AGE_MESSAGE);
        playerDelegate.createNewPlayer(new PlayerInfo("f", 0));
        playerDelegate.createNewPlayer(new PlayerInfo("f", -4));
    }

    @Test
    public void testCreateNewPlayer() throws BusinessException {
        String name = "name";
        int age = 4;
        ArgumentCaptor captor = ArgumentCaptor.forClass(Player.class);
        doReturn(new Player(name, age)).when(playerService).createPlayer(any());
        PlayerDelegate playerDelegate = new PlayerDelegate(playerService);
        PlayerInfo playerInfo = new PlayerInfo(name, age);
        PlayerInfo newPlayerInfo = playerDelegate.createNewPlayer(playerInfo);
        verify(playerService).createPlayer((Player) captor.capture());
        Player player = (Player) captor.getValue();
        assertEquals(player.getAge(), age);
        assertEquals(player.getName(), name);
        assertEquals(newPlayerInfo.getName(), name);
        assertEquals(newPlayerInfo.getAge(), age);
    }
}
