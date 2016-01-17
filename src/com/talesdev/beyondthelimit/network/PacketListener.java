package com.talesdev.beyondthelimit.network;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import org.java_websocket.WebSocket;

/**
 * @author MoKunz
 */
public interface PacketListener{
    void receivePacket(BeyondTheLimit app, WebSocket conn, Packet packet);
}
