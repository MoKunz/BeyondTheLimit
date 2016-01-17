package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Copycat
 * @author MoKunz
 */
public class CopyCat implements Item{
    @Override
    public String name() {
        return "CopyCat";
    }

    @Override
    public String displayName() {
        return "CopyCat";
    }

    @Override
    public String description() {
        return "คัดลอกไอเทม 1 ชิ้นจากช่องเก็บของโดยใส่ไอเทม CopyCat และไอเทมที่ต้องการจะคัดลอก 1 ชิ้นลงในช่องโจมตี ";
    }

    @Override
    public List<Boost> boosts() {
        return new ArrayList<>();
    }

    @Override
    public void attack(Game game) {
        Item lastItem = game.inventory().lastUsed();
        if(lastItem != null && lastItem.name().equals(name())){
            lastItem.attack(game);
        }
    }
}
