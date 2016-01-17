package com.talesdev.beyondthelimit.network;

import com.talesdev.beyondthelimit.BeyondTheLimit;

/**
 * @author MoKunz
 */
public class HelloWorldPacket extends Packet{
    public String name = "helloworld";
    public String message;
    public HelloWorldPacket(BeyondTheLimit app) {
        super(app);
    }

    @Override
    public PacketType packetType() {
        return PacketType.INOUT;
    }
}
