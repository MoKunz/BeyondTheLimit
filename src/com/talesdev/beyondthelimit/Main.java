package com.talesdev.beyondthelimit;

import org.fusesource.jansi.AnsiConsole;
import org.pmw.tinylog.Configurator;

public class Main {

    public static void main(String[] args) {
        //AnsiConsole.systemInstall();
        Configurator.currentConfig()
                .formatPattern("[{date:HH:mm:ss}][{thread}/{level}]: {message}")
                .activate();
        BeyondTheLimit app = new BeyondTheLimit();
        app.start();
        Thread main = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (app.running()) app.stop();
            try {
                main.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
