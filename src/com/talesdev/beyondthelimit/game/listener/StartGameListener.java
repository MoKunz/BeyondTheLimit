package com.talesdev.beyondthelimit.game.listener;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.GameStatus;
import com.talesdev.beyondthelimit.game.packet.StartGamePacket;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketListener;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author MoKunz
 */
public class StartGameListener implements PacketListener{
    @Override
    public void receivePacket(BeyondTheLimit app, WebSocket conn, Packet packet) {
        if(packet instanceof StartGamePacket && app.gameHost().currentStatus().equals(GameStatus.PREPARE)){
            app.gameHost().startGame();
            app.executorService().schedule(() -> {
                StartGamePacket p = new StartGamePacket(app);
                p.sendAll();
            },500, TimeUnit.MILLISECONDS);
            Logger.info("[Game] The game \"" + app.gameHost().currentRunning().teamName() + "\" has been started!");
        }
    }
}
