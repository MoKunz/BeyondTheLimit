package com.talesdev.beyondthelimit;

import com.talesdev.beyondthelimit.command.CommandExecutor;
import com.talesdev.beyondthelimit.network.HelloWorldPacket;
import jline.internal.Log;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

import java.util.Map;

/**
 * @author MoKunz
 */
public class SystemCommand implements CommandExecutor{
    @Override
    public boolean onCommand(BeyondTheLimit app, String[] args, String cmd) {
        if(cmd.equals("system") && args.length > 0){
            String sub = args[0];
            if(sub.equals("clientlist")){
                Logger.info("[System] Client list");
                for(Map.Entry<String,WebSocket> e : app.server().clientMap().entrySet()){
                    Logger.info(" - " + e.getKey() + " : " + e.getValue().getRemoteSocketAddress());
                }

            }
            else if(sub.equals("help")){
                Logger.info("[System] for the command list, ask MoKunz.");
            }
            else if(sub.equals("say")){
                if(args.length > 1){
                    String msg = args[1];
                    HelloWorldPacket packet = new HelloWorldPacket(app);
                    packet.message = msg;
                    packet.sendAll();
                    Logger.info("[Server] " + msg);
                }
            }
            else if (sub.equals("kick")) {
                if(args.length > 1){
                    String client = args[1];
                    WebSocket conn = app.server().findClient(client);
                    if(conn != null){
                        Logger.info("[Network] Kicked client \"" + app.server().clientName(conn) + "\".");
                        conn.close(1000, "Kicked by server");
                    }
                    else{
                        Logger.info("[Network] Client \"" + client + "\" not found!");
                    }
                }
            } else if (sub.equals("con")){
                app.server().connections().forEach((con) -> Logger.info(con.getRemoteSocketAddress()));
            }
            else if(sub.equals("restart")){
                app.restart();
            }
        }
        return true;
    }
}
