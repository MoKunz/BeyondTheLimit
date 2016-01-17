package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * BossUpdate
 * @author MoKunz
 */
public class BossUpdatePacket extends Packet {
    private String name = "bossupdate";
    public double health;
    public BossUpdatePacket(BeyondTheLimit app,double bossHealth) {
        super(app);
        this.health = bossHealth;
    }

    @Override
    public PacketType packetType() {
        return PacketType.OUT;
    }
}
