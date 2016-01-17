package com.talesdev.beyondthelimit.game.item;

import com.talesdev.beyondthelimit.BeyondTheLimit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MoKunz
 */
public class ItemFactory {
    private Map<String,Class<? extends Item>> itemMap;
    private BeyondTheLimit app;

    public ItemFactory(BeyondTheLimit app) {
        this.app = app;
        this.itemMap = new HashMap<>();
    }

    public Item create(String itemName){
        if(findItemClass(itemName) != null){
            try {
                return findItemClass(itemName).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Class<? extends Item> findItemClass(String itemName) {
        for (Map.Entry<String, Class<? extends Item>> e : itemMap.entrySet()) {
            if (e.getKey().equals(itemName)) {
                return e.getValue();
            }
        }
        return null;
    }

    public void registerItem(String itemName, Class<? extends Item> itemClass){
        itemMap.put(itemName,itemClass);
    }
}
