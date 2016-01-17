package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.game.Game;

/**
 * @author MoKunz
 */
public class NullItemActive implements ItemActive{
    @Override
    public String name() {
        return "null";
    }

    @Override
    public String description() {
        return "active1: null";
    }

    @Override
    public void active(Game game, Item item) {
        // DO NOTHING
    }
}
