package com.talesdev.beyondthelimit.network;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.packet.GameUpdatePacket;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

/**
 * Packet
 * @author MoKunz
 */
public abstract class Packet {
    private transient BeyondTheLimit app;
    public Packet(BeyondTheLimit app) {
        this.app = app;
    }

    public abstract PacketType packetType();

    public void send(WebSocket conn){
        conn.send(app.gson().toJson(this));
        if(!(this instanceof GameUpdatePacket)) Logger.info("Packet " + this.getClass().getSimpleName() + " has been sent to " + app.server().clientName(conn));
    }

    public void send(String name){
        WebSocket client = app.server().findClient(name);
        if(client != null){
            send(client);
        }
    }

    public void sendAll(){
        app.server().clientMap().values().forEach(this::send);
    }

    public BeyondTheLimit app(){
        return app;
    }
}
