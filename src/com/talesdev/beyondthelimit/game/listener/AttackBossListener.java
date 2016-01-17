package com.talesdev.beyondthelimit.game.listener;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.BossDamage;
import com.talesdev.beyondthelimit.game.Game;
import com.talesdev.beyondthelimit.game.GameStatus;
import com.talesdev.beyondthelimit.game.packet.AttackBossPacket;
import com.talesdev.beyondthelimit.game.packet.GameUpdatePacket;
import com.talesdev.beyondthelimit.game.packet.ScoreChangePacket;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreEntry;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketListener;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

/**
 * @author MoKunz
 */
public class AttackBossListener implements PacketListener{
    private static final double BASE_DAMAGE = 1000;
    @Override
    public void receivePacket(BeyondTheLimit app, WebSocket conn, Packet packet) {
        if(packet instanceof AttackBossPacket){
            if(!app.gameHost().currentStatus().equals(GameStatus.PLAYING)){
                return;
            }
            AttackBossPacket p = ((AttackBossPacket) packet);
            Game game =  app.gameHost().currentRunning();
            double amount = new BossDamage(game.boss(), p.level).value();
            game.boss().damage(amount);
            int score = (int)amount;
            game.addScore(score);
            new ScoreChangePacket(game.app(),score).sendAll();
            Logger.info("[Game] The boss has been attacked! (health changed to " + game.boss().health() + ")");
            GameUpdatePacket gp = new GameUpdatePacket(app, new ScoreEntry(game));
            gp.sendAll();
        }
    }
}
