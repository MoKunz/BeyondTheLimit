package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MoKunz
 */
public class DeadlyEnchantAttack implements Item{
    @Override
    public String name() {
        return "DeadlyEnchantAttack";
    }

    @Override
    public String displayName() {
        return "Deadly Enchant Attack";
    }

    @Override
    public String description() {
        return "โจมตี 4000";
    }

    @Override
    public List<Boost> boosts() {
        return new ArrayList<>();
    }

    @Override
    public void attack(Game game) {
        game.boss().damage(4000);
    }
}
