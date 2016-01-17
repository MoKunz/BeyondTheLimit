package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.game.Game;

/**
 * @author MoKunz
 */
public interface ItemActive {
    String name();
    String description();
    void active(Game game, Item item);
}
