package com.talesdev.beyondthelimit.game;

/**
 * @author MoKunz
 */
public enum GameStatus {
    PREPARE("Preparing game"),PLAYING("In game"),END("Game ended");
    private String msg;
    GameStatus(String msg){
        this.msg = msg;
    }
    public String getMessage(){return msg;}
}
