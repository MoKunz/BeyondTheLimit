package com.talesdev.beyondthelimit.game.listener;

import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.item.Item;
import com.talesdev.beyondthelimit.game.packet.AddItemPacket;
import com.talesdev.beyondthelimit.network.Packet;
import com.talesdev.beyondthelimit.network.PacketListener;
import org.java_websocket.WebSocket;
import org.pmw.tinylog.Logger;

/**
 * @author MoKunz
 */
public class AddItemListener implements PacketListener{
    @Override
    public void receivePacket(BeyondTheLimit app, WebSocket conn, Packet packet) {
        if(packet instanceof AddItemPacket){
            AddItemPacket ap = ((AddItemPacket) packet);
            Item item = app.itemFactory().create(ap.itemName);
            if(item != null){
                app.gameHost().currentRunning().inventory().addItem(item);
                Logger.info("[Game] New item added (" + ap.itemName + ")");
            }
        }
    }
}
