package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * @author MoKunz
 */
public class SetDataPacket extends Packet{
    private String name = "setdata";
    private int scoreChange;
    private int timeChange;
    public SetDataPacket(BeyondTheLimit app, int scoreChange, int timeChange) {
        super(app);
        this.scoreChange = scoreChange;
        this.timeChange = timeChange;
    }

    public int scoreChange() {
        return scoreChange;
    }

    public int timeChange() {
        return timeChange;
    }

    @Override
    public PacketType packetType() {
        return PacketType.IN;
    }
}
