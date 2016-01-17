package com.talesdev.beyondthelimit.game.scoreboard;

import com.talesdev.beyondthelimit.game.Game;

import java.util.Comparator;

/**
 * @author MoKunz
 */
public class GameComparator implements Comparator<Game> {
    @Override
    public int compare(Game o1, Game o2) {
        return o1.score() - o2.score();
    }
}
