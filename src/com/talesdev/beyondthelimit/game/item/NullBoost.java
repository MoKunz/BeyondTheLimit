package com.talesdev.beyondthelimit.game.item;

/**
 * @author MoKunz
 */
public class NullBoost implements Boost{
    @Override
    public String name() {
        return "nullboost";
    }

    @Override
    public String displayName() {
        return "Boost: null";
    }

    @Override
    public double multiplier() {
        return 0;
    }
}
