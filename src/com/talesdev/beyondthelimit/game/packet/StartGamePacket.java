package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * @author MoKunz
 */
public class StartGamePacket extends Packet {
    private String name = "startgame";
    public StartGamePacket(BeyondTheLimit app) {
        super(app);
    }

    @Override
    public PacketType packetType() {
        return PacketType.IN;
    }
}
