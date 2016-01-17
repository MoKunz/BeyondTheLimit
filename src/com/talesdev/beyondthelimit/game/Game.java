package com.talesdev.beyondthelimit.game;

import com.talesdev.beyondthelimit.BeyondTheLimit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Represent a game
 * @author MoKunz
 */
public class Game {
    private BeyondTheLimit app;
    private Date start, end;
    private List<String> players;
    private int teamScore;
    private String teamName;
    private Boss boss;
    private GameStatus status;
    private GameTimer timer;
    private Inventory inventory;

    public Game(BeyondTheLimit app,String teamName,List<String> players) {
        this.app = app;
        this.players = players;
        this.teamName = teamName;
        this.status = GameStatus.PREPARE;
        this.timer = new GameTimer(this,300);
        this.inventory = new Inventory(this);
        this.boss = new Boss(this,512000);
    }

    public List<String> playerList(){
        return new ArrayList<>(players);
    }

    public String teamName(){
        return teamName;
    }

    public BeyondTheLimit app(){
        return app;
    }

    public GameTimer timer(){
        return timer;
    }

    public GameStatus currentStatus(){
        return status;
    }

    public Inventory inventory(){
        return inventory;
    }

    public Boss boss(){
        return boss;
    }

    public int score(){
        return teamScore;
    }

    public void changeScore(int to){
        this.teamScore = to;
    }

    public void addScore(int total){
        this.teamScore += total;
    }

    public void changeStatus(GameStatus status){
        this.status = status;
    }

    public void start(){
        start = Date.from(Instant.now());
        timer.start();
    }

    public void end(){
        end = Date.from(Instant.now());
        timer.stop();
    }
}
