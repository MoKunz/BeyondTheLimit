package com.talesdev.beyondthelimit.game.scoreboard;

import com.talesdev.beyondthelimit.game.Game;

/**
 * @author MoKunz
 */
public class ScoreEntry {
    private String teamName = "";
    private int score = 0;
    private int timeLeft = 0;

    public ScoreEntry(String teamName, int score, int timeLeft) {
        this.teamName = teamName;
        this.score = score;
        this.timeLeft = timeLeft;
    }

    public ScoreEntry(Game game){
        this(game.teamName(),game.score(),(int)game.timer().currentTime());
    }

    public String teamName() {
        return teamName;
    }

    public int timeLeft() {
        return timeLeft;
    }

    public int score() {
        return score;
    }
}
