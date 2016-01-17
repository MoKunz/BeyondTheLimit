package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreEntry;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * @author MoKunz
 */
public class GameUpdatePacket extends Packet {
    private String name = "gameupdate";
    private ScoreEntry data;
    public GameUpdatePacket(BeyondTheLimit app, ScoreEntry scoreEntry) {
        super(app);
        this.data = scoreEntry;
    }

    public ScoreEntry data(){
        return data;
    }

    @Override
    public PacketType packetType() {
        return PacketType.OUT;
    }
}
