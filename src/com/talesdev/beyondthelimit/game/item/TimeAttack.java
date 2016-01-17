package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MoKunz
 */
public class TimeAttack implements Item{
    @Override
    public String name() {
        return "TimeAttack";
    }

    @Override
    public String displayName() {
        return "Time Attack";
    }

    @Override
    public String description() {
        return "เวลาที่เหลือน้อยลงจะทำให้การโจมตีใส่บอสรุนแรงมากขึ้นตั้งแต่  x1.5 ถึง x5";
    }

    @Override
    public List<Boost> boosts() {
        return new ArrayList<>();
    }

    @Override
    public void attack(Game game) {
        double a = (749 - 4*game.timer().currentTime())/149;
        double multiplier = (a <= 0) ? 0 : a;
        game.boss().damage(10000.0 *multiplier);
    }
}
