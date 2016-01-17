package com.talesdev.beyondthelimit.network;

import com.talesdev.beyondthelimit.BeyondTheLimit;

/**
 * @author MoKunz
 */
public class SuccessPacket extends Packet{
    private String name = "success";

    public SuccessPacket(BeyondTheLimit app) {
        super(app);
    }

    @Override
    public PacketType packetType() {
        return PacketType.INOUT;
    }
}
