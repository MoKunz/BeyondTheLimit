package com.talesdev.beyondthelimit.game;

import java.util.Random;

/**
 * @author MoKunz
 */
public class BossDamage {
    private final Boss boss;
    private Level level;
    private static final double lvl = 1;

    public BossDamage(Boss boss,Level level) {
        this.boss = boss;
        this.level = level;
    }

    public double value(){
        Random random = new Random();
        return random.nextInt((level.max - level.min) + 1) + level.min;
    }

    public enum Level{
        LOW(10000,40000),MID(40000,80000),HIGH(80000,150000);
        private int min, max;
        Level(int min,int max){
            this.min = min;
            this.max = max;
        }
        public int min(){return min;}
        public int max(){return max;}
    }
}
