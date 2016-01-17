package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * @author MoKunz
 */
public class GameEndPacket extends Packet{
    private String name = "gameend";
    /**
     * Game end reason
     *  0 : boss killed
     *  1 : times up and not killed
     */
    public int reason;
    public GameEndPacket(BeyondTheLimit app,int reason) {
        super(app);
        this.reason = reason;
    }

    @Override
    public PacketType packetType() {
        return PacketType.OUT;
    }
}
