package com.talesdev.beyondthelimit.network;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.command.Command;
import com.talesdev.beyondthelimit.command.CommandSystem;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;
import org.pmw.tinylog.Logger;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author MoKunz
 */
public class BeyondTheLimitServer extends WebSocketServer {
    private ConcurrentMap<String,WebSocket> clientsMap = new ConcurrentHashMap<>();
    private BeyondTheLimit app;
    public BeyondTheLimitServer(BeyondTheLimit app, InetSocketAddress address) {
        super(address);
        this.app = app;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("welcome");
        Logger.info("[Network] Client connected!");
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn,Draft draft, ClientHandshake request) throws InvalidDataException {
        Logger.info("Handshake received from " + conn.getRemoteSocketAddress());
        return super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conn.send("goodbye");
        Logger.info("[Network] Client state : " + conn.getReadyState());
        Logger.info("[Network] Client " + clientName(conn) + " disconnected (" + code + "): " + reason);
        clientsMap.remove(clientName(conn));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        CommandSystem system = app.commandSystem();
        Command cmd = new Command(message);
        if(cmd.getCmd().equals("setname")){
            if(cmd.getArgs()[0] != null){
                handleNameSet(conn, cmd.getArgs()[0]);
                return;
            }
        }
        // packet interceptor
        ProtocolSystem p = app.protocol();
        Class<? extends Packet> pClass = p.findPacketClass(message);
        if(pClass != null){
            Logger.info("[Network] Client \"" + clientName(conn) + "\" sent a packet (" + pClass.getSimpleName() + ")");
            p.receivePacket(conn,p.createPacket(message, pClass));
        }
        else{
            Logger.info("[Network] Client \"" + clientName(conn) + "\" sent an unknown packet type!");
        }
    }

    private void handleNameSet(WebSocket conn,String name){
        clientsMap.put(name,conn);
        Logger.info("[Network] Client name has been set (" + name + ").");
    }

    public WebSocket findClient(String name){
        return clientsMap.get(name);
    }

    public String clientName(WebSocket conn){
        for(Map.Entry<String,WebSocket> e : clientsMap.entrySet()){
            if(e.getValue().equals(conn)){
                return e.getKey();
            }
        }
        return null;
    }

    public Map<String,WebSocket> clientMap(){
        return clientsMap;
    }

    public Collection<WebSocket> clients(){
        return clientsMap.values();
    }

    public boolean hasClient(String name){
        return clientsMap.containsKey(name);
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {

    }
}
