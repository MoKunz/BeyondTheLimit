package com.talesdev.beyondthelimit.game;

import com.talesdev.beyondthelimit.game.item.Item;
import com.talesdev.beyondthelimit.game.packet.ItemUpdatePacket;
import com.talesdev.beyondthelimit.game.packet.ScoreChangePacket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author MoKunz
 */
public class Inventory {
    private Game game;
    private List<Item> itemList;
    private Item lastUsed;
    public Inventory(Game game) {
        this.game = game;
        this.itemList = new ArrayList<>();
    }
    public Game game(){
        return game;
    }
    public List<Item> items(){
        return new ArrayList<>(itemList);
    }
    public Item findItem(String item){
        for(Item i : itemList){
            if(i.name().equals(item)) return i;
        }
        return null;
    }
    public void removeItem(String item){
        Item i = findItem(item);
        itemList.remove(itemList.indexOf(i));
    }

    public void useItem(String item){
        Item i = findItem(item);
        if(i != null){
            double before, after;
            before = game.boss().health();
            i.attack(game);
            after = game.boss().health();
            int score = (int)(Math.round(before - after));
            game.addScore(score);
            new ScoreChangePacket(game.app(),score).sendAll();
            lastUsed = i;
            removeItem(i.name());
        }
    }

    public Item lastUsed(){
        return lastUsed;
    }

    @Deprecated
    public double calculateBasicDamage(){
        return 0.0;
    }

    public void addItem(Item item) {
        itemList.add(item);
        ItemUpdatePacket ip = new ItemUpdatePacket(game.app(),item);
        ip.sendAll();
    }

    public int size(){
        return itemList.size();
    }
}
