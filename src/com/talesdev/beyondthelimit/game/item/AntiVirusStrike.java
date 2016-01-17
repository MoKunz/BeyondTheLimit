package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author MoKunz
 */
public class AntiVirusStrike implements Item{
    @Override
    public String name() {
        return "AntiVirusStrike";
    }

    @Override
    public String displayName() {
        return "AntiVirus Strike";
    }

    @Override
    public String description() {
        return "ทำความเสียหาย 50,000 – 100,000 ";
    }

    @Override
    public List<Boost> boosts() {
        return new ArrayList<>();
    }

    @Override
    public void attack(Game game) {
        game.boss().damage(50000 + new Random().nextInt(50001));
    }
}
