package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * @author MoKunz
 */
public class ScoreChangePacket extends Packet{
    private String name = "scorechange";
    public int score;
    public ScoreChangePacket(BeyondTheLimit app,int score) {
        super(app);
        this.score = score;
    }

    @Override
    public PacketType packetType() {
        return PacketType.OUT;
    }
}
