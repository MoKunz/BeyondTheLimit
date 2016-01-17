package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.game.Boss;
import com.talesdev.beyondthelimit.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author MoKunz
 */
public class LuckyCoin implements Item{

    @Override
    public String name() {
        return "LuckyCoin";
    }

    @Override
    public String displayName() {
        return "Lucky Coin";
    }

    @Override
    public String description() {
        return "ทอยเหรียญหัวและก้อย หากผลออกมาเป็นหัวโจมตี x3 หากผลออกมาเป็นก้อย x0.25";
    }

    @Override
    public List<Boost> boosts() {
        return new ArrayList<>();
    }

    @Override
    public void attack(Game game) {
        Boss boss = game.boss();
        if(new Random().nextBoolean()){
            boss.damage(10000.0*3);
        }
        else{
            boss.damage(10000.0*0.25);
        }
    }
}
