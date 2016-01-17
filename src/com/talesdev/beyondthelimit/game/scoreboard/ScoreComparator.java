package com.talesdev.beyondthelimit.game.scoreboard;

import java.util.Comparator;

/**
 * @author MoKunz
 */
public class ScoreComparator implements Comparator<ScoreEntry>{
    @Override
    public int compare(ScoreEntry o1, ScoreEntry o2) {
        if(o2.timeLeft() == o1.timeLeft()){
            return o2.score() - o1.score();
        }
        else{
            return o2.timeLeft() - o1.timeLeft();
        }
    }
}
