package com.battleground.battleground.models;

public class Statistics {
    private int battles;
    private int goldWon;
    private int damageDealt;

    public Statistics() {
    }

    public int getBattles() {
        return battles;
    }

    public void setBattles(int battles) {
        this.battles = battles;
    }

    public int getGoldWon() {
        return goldWon;
    }

    public void setGoldWon(int goldWon) {
        this.goldWon = goldWon;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }
}
