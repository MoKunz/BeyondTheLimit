package com.talesdev.beyondthelimit.command;

import java.util.Arrays;

/**
 * @author MoKunz
 */
public class Command {
    private String cmd;
    private String[] args;

    public Command(String cmd, String[] args) {
        this.cmd = cmd;
        this.args = args;
    }

    public Command(String rawMsg){
        String[] sp = rawMsg.split("\\b\\s");
        this.cmd = sp[0];
        this.args = new String[sp.length - 1];
        System.arraycopy(sp, 1, args, 0, args.length);
    }

    public String getCmd() {
        return cmd;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "Command{" +
                "cmd='" + cmd + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
