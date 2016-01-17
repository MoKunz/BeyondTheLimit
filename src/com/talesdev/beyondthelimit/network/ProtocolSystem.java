package com.talesdev.beyondthelimit.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.talesdev.beyondthelimit.BeyondTheLimit;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author MoKunz
 */
public final class ProtocolSystem {
    private BeyondTheLimit app;
    // listener
    private ConcurrentMap<Class<? extends Packet>,PacketListener> listeners;
    private ConcurrentMap<String,Class<? extends Packet>> packetMap;
    public ProtocolSystem(BeyondTheLimit app) {
        this.app = app;
        this.listeners = new ConcurrentHashMap<>();
        this.packetMap = new ConcurrentHashMap<>();
    }

    public <T extends Packet> T createPacket(String rawPacket, Class<T> clazz){
        return app.gson().fromJson(rawPacket,clazz);
    }

    public Class<? extends Packet> findPacketClass(String msg){
        JsonParser parser = new JsonParser();
        JsonElement e = null;
        try {
            e = parser.parse(msg);
        } catch (JsonSyntaxException e1) {
            e1.printStackTrace();
        }
        if(e != null && e.isJsonObject()){
            JsonObject o = e.getAsJsonObject();
            if(o.has("name")){
                return packetMap.get(o.get("name").getAsString());
            }
        }
        return null;
    }

    public <T extends Packet> void registerPacket(String name, Class<T> packetClass){
        packetMap.put(name, packetClass);
    }

    public <T extends Packet> void listen(Class<T> packetClass, PacketListener listener){
        listeners.put(packetClass,listener);
    }

    public void sendPacket(WebSocket conn, Packet packet){
        conn.send(app.gson().toJson(packet));
    }

    public void receivePacket(WebSocket conn, Packet packet){
        if(app.debug) Logger.info("Packet from client : " + app.server().clientName(conn) + ", " + app.gson().toJson(packet));
        listeners.entrySet().stream().filter(e -> e.getKey() == packet.getClass()).forEach(e -> {
            e.getValue().receivePacket(app, conn, packet );
        });
    }
}
