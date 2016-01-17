package com.talesdev.beyondthelimit.game.listener;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.Game;
import com.talesdev.beyondthelimit.game.packet.GameUpdatePacket;
import com.talesdev.beyondthelimit.game.packet.ScoreboardPacket;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreEntry;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketListener;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

/**
 * @author MoKunz
 */
public class ScoreboardListener implements PacketListener {
    @Override
    public void receivePacket(BeyondTheLimit app, WebSocket conn, Packet packet) {
        // HACK
        Game current = app.gameHost().currentRunning();Logger.info("Test1");
        //if(!app.gameHost().has(current)){
        //    Logger.info("Test1.5");
        //    app.gameHost().add(current.teamName(), (int) current.timer().currentTime(), current.score());
        //}
        Logger.info("Test2");
        ScoreboardPacket sp = new ScoreboardPacket(app);
        Logger.info("Test2");
        sp.sendAll();
        GameUpdatePacket gp = new GameUpdatePacket(app,new ScoreEntry(app.gameHost().currentRunning()));
        gp.sendAll();
        Logger.info("[Game] Scoreboard has been sent!");
    }
}
