package com.talesdev.beyondthelimit.game.listener;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketListener;
import org.java_websocket.WebSocket;

/**
 * @author MoKunz
 */
public class ToCreateTeamListener implements PacketListener{
    @Override
    public void receivePacket(BeyondTheLimit app, WebSocket conn, Packet packet) {
        packet.sendAll();
    }
}
