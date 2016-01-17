package com.talesdev.beyondthelimit;

import com.talesdev.beyondthelimit.command.Command;
import com.talesdev.beyondthelimit.network.HelloWorldPacket;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.ProtocolSystem;
import jline.console.ConsoleReader;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author MoKunz
 */
public class ConsoleSystem extends Thread {
    private ConsoleReader console;
    private BeyondTheLimit app;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleSystem(BeyondTheLimit app) {
        super("Console");
        this.app = app;
        try {
            this.console = new ConsoleReader(System.in,System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (app.running()) {
            String line;
            line = scanner.nextLine();
            if(line != null){
                // Command processor
                String[] bc = line.split("\\b\\:");
                // send packet
                if(bc.length > 1){
                    WebSocket client = app.server().findClient(bc[0]);
                    if(app.protocol().findPacketClass(bc[1]) != null){
                        if(client != null){
                            sendPacket(client, bc[1]);
                            Logger.info("[Network] Packet \"" + bc[1] + "\" has been sent to " + bc[0] + "!");
                        }
                        else if(bc[0].equals("ALL")){
                            Logger.info("[Network] Packet \"" + bc[1] + "\" has been sent to all clients! ");
                            for(WebSocket s : app.server().clients()){
                                sendPacket(s, bc[1]);
                            }
                        }
                        else{
                            Logger.info("[Network] Client \"" + bc[0] + "\" not found!");
                        }
                    }
                    else{
                        Logger.info("[Network] Packet not found!");
                    }
                    continue;
                }
                Command cmd = new Command(line);
                if(cmd.getCmd().equals("stop")){
                    app.stop();
                    break;
                }
                app.commandSystem().runCommand(cmd.getCmd(),cmd.getArgs());
            }
        }
    }

    private void sendPacket(WebSocket conn, String rawPacket){
        ProtocolSystem p = app.protocol();
        Packet packet = p.createPacket(rawPacket,p.findPacketClass(rawPacket));
        p.sendPacket(conn, packet);
    }
}
