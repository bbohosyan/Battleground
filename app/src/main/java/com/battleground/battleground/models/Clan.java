package com.battleground.battleground.models;

import java.util.Set;

public class Clan {
    private String name;
    private Set<User> users;
    private int points;

    public Clan() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
