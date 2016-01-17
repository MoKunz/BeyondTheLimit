package com.talesdev.beyondthelimit;

import com.google.gson.Gson;
import com.talesdev.beyondthelimit.command.CommandSystem;
import com.talesdev.beyondthelimit.game.GameHost;
import com.talesdev.beyondthelimit.game.command.GameCommand;
import com.talesdev.beyondthelimit.game.item.*;
import com.talesdev.beyondthelimit.game.listener.*;
import com.talesdev.beyondthelimit.game.packet.*;
import com.talesdev.beyondthelimit.network.*;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author MoKunz
 */
public class BeyondTheLimit {
    private AtomicBoolean running = new AtomicBoolean(true);
    private BeyondTheLimitServer server;
    private ConsoleSystem consoleSystem;
    private CommandSystem commandSystem;
    private ProtocolSystem protocolSystem;
    private GameHost gameHost;
    private ItemFactory itemFactory;
    private ScheduledExecutorService service;
    private InetSocketAddress address;
    private Gson gson;
    public boolean debug = true;

    public BeyondTheLimit() {
        Logger.info("[System] Initializing...");
        this.address = new InetSocketAddress(8080);
        this.server = new BeyondTheLimitServer(this, address);
        this.service = Executors.newScheduledThreadPool(4);
        this.gson = new Gson();
        this.consoleSystem = new ConsoleSystem(this);
        this.commandSystem = new CommandSystem(this);
        this.protocolSystem = new ProtocolSystem(this);
        this.itemFactory = new ItemFactory(this);
        this.gameHost = new GameHost(this);
        this.gameHost.loadData();
    }

    public ScheduledExecutorService executorService() {
        return service;
    }

    public CommandSystem commandSystem() {
        return commandSystem;
    }

    public ProtocolSystem protocol(){
        return protocolSystem;
    }

    public GameHost gameHost(){
        return gameHost;
    }

    public BeyondTheLimitServer server() {
        return server;
    }

    public ItemFactory itemFactory(){
        return itemFactory;
    }

    public Gson gson(){
        return gson;
    }

    public void start() {
        Logger.info("[System] Starting server on " + address.toString() + ".");
        server.start();
        consoleSystem.start();
        // cmd
        commandSystem.registerCommand("system", new SystemCommand());
        commandSystem.registerCommand("game", new GameCommand());
        // packet
        protocolSystem.registerPacket("helloworld", HelloWorldPacket.class);
        protocolSystem.registerPacket("newgame", NewGamePacket.class);
        protocolSystem.registerPacket("gamescoreboard", ScoreboardPacket.class);
        protocolSystem.registerPacket("startgame", StartGamePacket.class);
        protocolSystem.registerPacket("gameupdate", GameUpdatePacket.class);
        protocolSystem.registerPacket("setdata", SetDataPacket.class);
        protocolSystem.registerPacket("gameend", GameEndPacket.class);
        //protocolSystem.registerPacket("attackboss", AttackBossPacket.class);
        protocolSystem.registerPacket("bossupdate", BossUpdatePacket.class);
        protocolSystem.registerPacket("itemupdate", ItemUpdatePacket.class);
        protocolSystem.registerPacket("additem", AddItemPacket.class);
        protocolSystem.registerPacket("scorechange", ScoreChangePacket.class);
        protocolSystem.registerPacket("tocreateteam", ToCreateTeamPacket.class);
        protocolSystem.registerPacket("scoreboard", ScoreboardPacket.class);
        protocolSystem.listen(NewGamePacket.class, new NewGameListener());
        protocolSystem.listen(StartGamePacket.class, new StartGameListener());
        protocolSystem.listen(SetDataPacket.class, new SetDataListener());
        protocolSystem.listen(AttackBossPacket.class, new AttackBossListener());
        protocolSystem.listen(AddItemPacket.class, new AddItemListener());
        protocolSystem.listen(ToCreateTeamPacket.class, new ToCreateTeamListener());
        protocolSystem.listen(ScoreboardPacket.class, new ScoreboardListener());
        protocolSystem.listen(HelloWorldPacket.class, (app, conn, packet) -> {
            if (packet instanceof HelloWorldPacket)
                Logger.info("Message from client : " + ((HelloWorldPacket) packet).message);
        });
        // item
        itemFactory.registerItem("CopyCat", CopyCat.class);
        itemFactory.registerItem("DeadlyEnchantAttack", DeadlyEnchantAttack.class);
        itemFactory.registerItem("TimeAttack", TimeAttack.class);
        itemFactory.registerItem("AntiVirusStrike", AntiVirusStrike.class);
        itemFactory.registerItem("LuckyCoin", LuckyCoin.class);
        Logger.info("[System] Done! For help, type \"help\" for the list of commands.");
        while (running()) {
            this.run();
        }
        // stop
        Logger.info("[System] Stopping the server.");
        this.onStop();
    }

    protected void run() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void onStop() {
        try {
            server.stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        consoleSystem.interrupt();
        service.shutdown();
        gameHost.saveData();
        gameHost.clear();
        Logger.info("[System] Server stopped!");
    }

    public boolean running() {
        return running.get();
    }

    public void stop() {
        running.set(false);
    }


    public void restart() {
        stop();
        start();
    }
}
