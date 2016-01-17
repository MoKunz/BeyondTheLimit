package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * @author MoKunz
 */
public class ToCreateTeamPacket extends Packet {
    private String name = "tocreateteam";
    public ToCreateTeamPacket(BeyondTheLimit app) {
        super(app);
    }

    @Override
    public PacketType packetType() {
        return PacketType.INOUT;
    }
}
