package com.battleground.battleground.models;

public class Hero {
    public static final int SPIDERMAN_ATTACK = 1;
    public static final int SPIDERMAN_DEFENCE = 3;
    public static final int BATMAN_ATTACK = 2;
    public static final int BATMAN_DEFENCE = 2;
    public static final int WONDERWOMAN_ATTACK = 4;
    public static final int WONDERWOMAN_DEFENCE = 0;
    public String name;
    public int attack;
    public int defence;

    public Hero(){}

    public Hero(String name, int attack, int defence) {
        setName(name);
        setAttack(attack);
        setDefence(defence);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
