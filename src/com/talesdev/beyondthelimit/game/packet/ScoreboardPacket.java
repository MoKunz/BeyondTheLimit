package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.Game;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreComparator;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreEntry;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;
import org.pmw.tinylog.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Scoreboard init packet
 * @author MoKunz
 */
public class ScoreboardPacket extends Packet{
    private String name = "scoreboard";
    public boolean request;
    public static transient final Comparator<ScoreEntry> COMPARATOR = new ScoreComparator();
    public static transient final int MAX_RECORDS = 5;
    private List<ScoreEntry> data;

    public ScoreboardPacket(BeyondTheLimit app) {
        super(app);
        data = new ArrayList<>();
        int max = 1;
        for(ScoreEntry game : app.gameHost().previousGame()){
            if(max > 10){
                break;
            }
            data.add(game);
            max++;
        }
        request = false;
    }

    public SortedSet<ScoreEntry> scoreEntries(){
        return new TreeSet<>(data);
    }

    @Override
    public PacketType packetType() {
        return PacketType.OUT;
    }
}
