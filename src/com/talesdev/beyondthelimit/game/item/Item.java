package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.game.Game;

import java.util.List;

/**
 *
 * @author MoKunz
 */
public interface Item {
    // aka id
    String name();
    String displayName();
    String description();
    List<Boost> boosts();
    void attack(Game game);
}
