package com.talesdev.beyondthelimit.game.listener;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.Game;
import com.talesdev.beyondthelimit.game.packet.NewGamePacket;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketListener;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;

/**
 * @author MoKunz
 */
public class NewGameListener implements PacketListener {
    @Override
    public void receivePacket(BeyondTheLimit app, WebSocket conn, Packet packet) {
        if(packet instanceof NewGamePacket){
            NewGamePacket np = ((NewGamePacket) packet);
            Game game = new Game(app,np.teamName,new ArrayList<>());
            app.gameHost().hostGame(game);
            Logger.info("[Game] New game create with name \"" + np.teamName + "\" at " + Date.from(Instant.now()).toString());
        }
    }
}
