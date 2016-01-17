package com.talesdev.beyondthelimit.command;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import org.pmw.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MoKunz
 */
public class CommandSystem {
    private BeyondTheLimit app;
    private Map<String, CommandExecutor> commandMap = new HashMap<>();

    public CommandSystem(BeyondTheLimit app) {
        this.app = app;
    }

    public void registerCommand(String cmd, CommandExecutor executor) {
        commandMap.put(cmd, executor);
    }

    public void runCommand(String cmd, String[] args) {
        CommandExecutor executor = commandMap.get(cmd);
        if (executor != null) {
            try{
                executor.onCommand(app, args, cmd);
            }
            catch (Throwable e){
                Logger.error("[Command] Error while peforming command \"" + cmd + "\", " + e.getCause());
            }
        } else {
            Logger.info("[Command] Unknown command \"" + cmd + "\", type \"help\" for the help.");
        }
    }
}
