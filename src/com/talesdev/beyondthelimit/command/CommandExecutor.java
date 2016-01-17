package com.talesdev.beyondthelimit.command;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import org.java_websocket.WebSocket;

/**
 * @author MoKunz
 */
@FunctionalInterface
public interface CommandExecutor {
    public boolean onCommand(BeyondTheLimit app, String[] args, String cmd);
}
