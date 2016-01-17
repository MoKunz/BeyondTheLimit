package com.talesdev.beyondthelimit.game;

import com.talesdev.beyondthelimit.game.packet.GameUpdatePacket;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreEntry;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author MoKunz
 */
public class GameTimer {
    /**
     * Time in seconds
     */
    private AtomicLong currentTime, timeUsed;
    private Game game;
    private ScheduledFuture future;

    /**
     * Create game timer
     * @param game game instance
     * @param startingTime time starts
     */
    public GameTimer(Game game,long startingTime) {
        this.game = game;
        this.currentTime = new AtomicLong(startingTime);
        this.timeUsed = new AtomicLong(0);
    }

    public void start(){
        ScheduledExecutorService s = this.game.app().executorService();
        future = s.scheduleAtFixedRate(() -> tick(1),0,1, TimeUnit.SECONDS);
        game.changeStatus(GameStatus.PLAYING);
    }

    public void stop(){
        future.cancel(true);
        game.boss().die(game.boss().health() > 0);
        game.changeStatus(GameStatus.END);
        // HACK
        game.app().executorService().schedule(() -> {
            game.app().gameHost().add(new ScoreEntry(game));
        },1000,TimeUnit.MILLISECONDS);
    }

    /**
     * Current game time (countdown)
     * @return current time
     */

    public long currentTime(){
        long ct = currentTime.get();
        return ct < 0 ? 0 : ct;
    }

    /**
     * Get time used in the game
     * @return current time used
     */
    public long timeUsed(){
        return timeUsed.get();
    }

    /**
     * Add time
     * @param total time to be added
     */
    public void addTime(long total){
        currentTime.addAndGet(total);
    }

    /**
     * Update time of the timer
     * @param total total time passed
     */
    public void tick(long total){
        currentTime.addAndGet(-total);
        timeUsed.addAndGet(total);
        if(currentTime.get() <= 0){
            currentTime.set(0);
            stop();
        }
        // send packet
        GameUpdatePacket gp = new GameUpdatePacket(game.app(),new ScoreEntry(game));
        gp.sendAll();
    }

    /**
     * Game instance
     * @return game instance
     */
    public Game game(){
        return game;
    }
}
