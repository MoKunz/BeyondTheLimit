package com.talesdev.beyondthelimit.game;

import com.talesdev.beyondthelimit.game.packet.BossUpdatePacket;
import com.talesdev.beyondthelimit.game.packet.GameEndPacket;
import jline.internal.Log;

/**
 * Boss
 * @author MoKunz
 */
public class Boss {
    private Game game;
    public static final double MAXHEALTH = 500000;
    private double health;
    public Boss(Game game,double health) {
        this.game = game;
        this.health = MAXHEALTH;
    }

    /**
     * Deal a damage to boss
     * for healing use negative value
     * @param amount damage amount
     */
    public void damage(double amount){
        health -= amount;
        BossUpdatePacket bp = new BossUpdatePacket(game.app(), health);
        bp.sendAll();
        if(health > MAXHEALTH) health = MAXHEALTH;
        if(health <= 0){
            health = 0;
            //game.end();
            game.timer().stop();
        }
    }

    public void die(boolean timesUp){
        Log.info("[Game] The boss died! ( timesup = " + timesUp + " )");
        Thread.dumpStack();
        GameEndPacket packet = new GameEndPacket(game.app(), timesUp ? 1 : 0);
        packet.sendAll();
    }

    public double health(){
        return health;
    }

    public Game game() {
        return game;
    }
}
