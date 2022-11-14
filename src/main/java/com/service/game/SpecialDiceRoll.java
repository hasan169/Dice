package com.service.game;

import com.config.ConfigProperties;
import com.constant.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component("diceRoll")
public class SpecialDiceRoll implements IDiceRoll {

    private static final Logger logger = LoggerFactory.getLogger(SpecialDiceRoll.class);

    private ConfigProperties configProperties;
    private String url;

    @Autowired
    public SpecialDiceRoll(ConfigProperties configProperties) {
        this.url = configProperties.getConfigValue(Properties.DICE_ROLL_URL);
        logger.info("Dice roll url {}", url);
    }

    @Override
    public int rollDice(int counter) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Integer> result = restTemplate.getForObject(url, HashMap.class);
        return result.get("score");
    }
}
