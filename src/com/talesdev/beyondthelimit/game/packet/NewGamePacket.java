package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * @author MoKunz
 */
public class NewGamePacket extends Packet{
    private String name = "newgame";
    public String teamName;

    public NewGamePacket(BeyondTheLimit app) {
        super(app);
    }

    @Override
    public PacketType packetType() {
        return PacketType.IN;
    }
}
