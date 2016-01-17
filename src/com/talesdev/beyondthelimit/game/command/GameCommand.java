package com.talesdev.beyondthelimit.game.command;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.command.CommandExecutor;
import com.talesdev.beyondthelimit.game.BossDamage;
import com.talesdev.beyondthelimit.game.Game;
import com.talesdev.beyondthelimit.game.GameStatus;
import com.talesdev.beyondthelimit.game.item.Item;
import com.talesdev.beyondthelimit.game.packet.GameUpdatePacket;
import com.talesdev.beyondthelimit.game.packet.ScoreChangePacket;
import com.talesdev.beyondthelimit.game.packet.ToCreateTeamPacket;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreEntry;
import com.talesdev.beyondthelimit.network.Packet;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

import java.util.ArrayList;

/**
 * @author MoKunz
 */
public class GameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(BeyondTheLimit app, String[] args, String cmd) {
        if (cmd.equals("game") && args.length > 0) {
            String subcmd = args[0];
            if (subcmd.equals("current")) {
                Game cg = app.gameHost().currentRunning();
                Logger.info("[Game] Current Game.");
                Logger.info(" - teamName : " + cg.teamName());
                Logger.info(" - status : " + cg.currentStatus());
                Logger.info(" - current time : " + cg.timer().currentTime());
                Logger.info(" - score : " + cg.score());
                Logger.info(" - health : " + cg.boss().health());
            } else if (subcmd.equals("setscore")) {
                if (args.length > 1) {
                    Game cg = app.gameHost().currentRunning();
                    cg.changeScore(Integer.parseInt(args[1]));
                    Logger.info("[Game] Score of \"" + cg.teamName() + "\" has been changed!");
                } else {
                    Logger.info("[Game] Missing score argument!");
                }
            } else if (subcmd.equals("addtime")) {
                if (args.length > 1) {
                    Game cg = app.gameHost().currentRunning();
                    cg.timer().addTime(Integer.parseInt(args[1]));
                    Logger.info("[Game] Time of \"" + cg.teamName() + "\" has been changed to " + cg.timer().currentTime() + "s !");
                } else {
                    Logger.info("[Game] Missing time argument!");
                }
            } else if (subcmd.equals("create")) {
                if (args.length > 1) {
                    Game cg = new Game(app, args[1], new ArrayList<>());
                    app.gameHost().hostGame(cg);
                    Logger.info("[Game] \"" + cg.teamName() + "\" has been created. ");
                } else {
                    Logger.info("[Game] Missing time argument!");
                }
            } else if (subcmd.equals("start")) {
                app.gameHost().startGame();
                Logger.info("[Game] The game \"" + app.gameHost().currentRunning().teamName() + "\" has been started!");
            } else if (subcmd.equals("end")) {
                app.gameHost().stopGame();
                Logger.info("[Game] The game \"" + app.gameHost().currentRunning().teamName() + "\" has been stopped!");
            } else if (subcmd.equals("attack")) {
                if (args.length > 1) {
                    app.gameHost().currentRunning().boss().damage(Double.parseDouble(args[1]));
                    Logger.info("[Game] The boss \"" + app.gameHost().currentRunning().teamName() + "\" has been damaged for " + Double.parseDouble(args[1]) + "!");
                } else {
                    Logger.info("[Game] Missing damage argument!");
                }
            } else if (subcmd.equals("additem")) {
                if (args.length > 1) {
                    Item item = app.itemFactory().create(args[1]);
                    if (item != null) {
                        app.gameHost().currentRunning().inventory().addItem(item);
                        Logger.info("[Game] New item added (" + args[1] + ")");
                    }
                } else {
                    Logger.info("[Game] Missing damage argument!");
                }
            } else if (subcmd.equals("tocreateteam")) {
                new ToCreateTeamPacket(app).sendAll();
                app.server().connections().forEach((con) -> con.close(1000));
                Logger.info("[Game] ToCreateTeam sent!");
            } else if (subcmd.equals("clear")) {
                app.gameHost().clear();
                Logger.info("[Game] cleared!");
            } else if (subcmd.equals("list")) {
                app.gameHost().asList().forEach((s) -> {Logger.info(s.teamName() + " , " + s.score() + " , " + s.timeLeft() + " s");});
            } else if (subcmd.equals("add")){
                if(args.length > 3){
                    app.gameHost().add(args[1],Integer.parseInt(args[2]),Integer.parseInt(args[3]));
                }
            }
            else if (subcmd.equals("reload")) {
                app.gameHost().currentRunning().end();
                app.gameHost().clear();
                for (WebSocket s : app.server().clients()) {
                    s.close(1000);
                }
            } else if (subcmd.equals("low")) {
                a(app.gameHost().currentRunning(), BossDamage.Level.LOW);
            } else if (subcmd.equals("mid")) {
                a(app.gameHost().currentRunning(), BossDamage.Level.MID);
            } else if (subcmd.equals("high")) {
                a(app.gameHost().currentRunning(), BossDamage.Level.HIGH);
            } else {
                Logger.info("[Game] Unknown subcommand \"" + subcmd + "\"!");
            }
        }
        return true;
    }

    public void a(Game game, BossDamage.Level level) {
        if (!game.app().gameHost().currentStatus().equals(GameStatus.PLAYING)) {
            return;
        }
        double amount = new BossDamage(game.boss(), level).value();
        game.boss().damage(amount);
        int score = (int) amount;
        game.addScore(score);
        new ScoreChangePacket(game.app(), score).sendAll();
        Logger.info("[Game] The boss has been attacked! (health changed to " + game.boss().health() + ")");
        GameUpdatePacket gp = new GameUpdatePacket(game.app(), new ScoreEntry(game));
        gp.sendAll();
    }
}
