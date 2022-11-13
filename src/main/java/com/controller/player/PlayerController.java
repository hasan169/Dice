package com.controller.player;

import com.constant.MessageConstant;
import com.delegate.player.IPlayerDelegate;
import com.exception.BusinessException;

import com.info.NewPlayerRequest;
import com.info.PlayerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private IPlayerDelegate playerDelegate;

    @PostMapping
    public ResponseEntity createNewPlayer(@RequestBody NewPlayerRequest newPlayerRequest) {
        try {
            PlayerInfo newPlayerInfo = new PlayerInfo(newPlayerRequest.getName(), newPlayerRequest.getAge());
            PlayerInfo playerInfo = playerDelegate.createNewPlayer(newPlayerInfo);
            return ResponseEntity.status(HttpStatus.OK).body(playerInfo);
        } catch (BusinessException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageConstant.SERVER_ERROR_MESSAGE);
        }
    }
}
