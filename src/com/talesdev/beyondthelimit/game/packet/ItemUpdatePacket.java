package com.talesdev.beyondthelimit.game.packet;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.item.Item;
import com.talesdev.beyondthelimit.game.item.ItemData;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketType;

/**
 * @author MoKunz
 */
public class ItemUpdatePacket extends Packet{
    private String name = "itemupdate";
    public String itemName;
    //public ItemData data;
    public ItemUpdatePacket(BeyondTheLimit app,Item item) {
        super(app);
        this.itemName = item.name();
        //this.data = new ItemData(item);
    }

    @Override
    public PacketType packetType() {
        return PacketType.OUT;
    }
}
