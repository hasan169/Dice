package com.controller.player;

import com.constant.MessageConstant;
import com.delegate.player.IPlayerDelegate;
import com.exception.BusinessException;

import com.info.NewPlayerRequest;
import com.info.PlayerInfo;
import com.info.PlayerScoreInfos;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private IPlayerDelegate playerDelegate;

    @PostMapping
    @ApiOperation(value = "Creating a new player", response = PlayerInfo.class)
    public ResponseEntity createNewPlayer(@RequestBody NewPlayerRequest newPlayerRequest) {
        try {
            logger.info("Received new player request {}", newPlayerRequest);
            PlayerInfo newPlayerInfo = new PlayerInfo(newPlayerRequest.getName(), newPlayerRequest.getAge());
            PlayerInfo playerInfo = playerDelegate.createNewPlayer(newPlayerInfo);
            return ResponseEntity.status(HttpStatus.OK).body(playerInfo);
        } catch (BusinessException e) {
            logger.error("Business error while creating new player ", e);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Exception while creating new player ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageConstant.SERVER_ERROR_MESSAGE);
        }
    }
}
