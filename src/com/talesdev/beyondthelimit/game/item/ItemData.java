package com.talesdev.beyondthelimit.game.item;

/**
 * @author MoKunz
 */
public class ItemData {
    public String name;
    public String displayName;
    public String description;
    public ItemData(Item item) {
        this.name = item.name();
        this.displayName = item.displayName();
        this.description = item.description();
    }
}
