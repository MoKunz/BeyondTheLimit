package com.talesdev.beyondthelimit.game.listener;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.Game;
import com.talesdev.beyondthelimit.game.GameStatus;
import com.talesdev.beyondthelimit.game.packet.SetDataPacket;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketListener;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

/**
 * @author MoKunz
 */
public class SetDataListener implements PacketListener {
    @Override
    public void receivePacket(BeyondTheLimit app, WebSocket conn, Packet packet) {
        if(packet instanceof SetDataPacket){
            SetDataPacket dp = ((SetDataPacket) packet);
            if(app.gameHost().currentStatus() == GameStatus.PLAYING){
                Game game = app.gameHost().currentRunning();
                game.timer().addTime(dp.timeChange());
                game.addScore(dp.scoreChange());
                Logger.info("[Game] Time and score has been changed! (" + dp.scoreChange() + "," + dp.timeChange() + ")");
            }
        }
    }
}
