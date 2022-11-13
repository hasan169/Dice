package com.service.game;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component("DiceRoll")
public class SpecialDiceRoll implements IDiceRoll {

    @Override
    public int rollDice(int counter) {
        final String uri = "http://developer-test.hishab.io/api/v1/roll-dice";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Integer> result = restTemplate.getForObject(uri, HashMap.class);
        return result.get("score");
    }
}
