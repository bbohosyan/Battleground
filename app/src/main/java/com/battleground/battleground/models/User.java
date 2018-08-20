package com.battleground.battleground.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private Map<String, Hero> heroes;
    private Statistics statistics;
    private Clan clan;
    private Team team;
    private Gender gender;
    private long yearsUser;
    private String dateUserRegistered;
    private long gold;
    private String email;
    private int strength;

    public User() {
    }

    public User(String email, Gender gender, String birthday) {
        setEmail(email);
        setGender(gender);
        setHeroes(new HashMap<String, Hero>());
        heroes.put(Hero.WONDERWOMAN_NAME, new Hero(Hero.WONDERWOMAN_NAME, Hero.WONDERWOMAN_ATTACK, Hero.WONDERWOMAN_DEFENCE));
        setClan(null);
        setTeam(Team.NOT_SET);
        setAge(birthday);
        setYearsUserH(birthday);
        setDateUserRegistered(LocalDate.now().toString());
        setGold(1000000);
        setStatistics(new Statistics());
        setStrength(2);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public Clan getClan() {
        return clan;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public String getDateUserRegistered() {
        return dateUserRegistered;
    }

    public void setDateUserRegistered(String dateUserRegistered) {
        this.dateUserRegistered = dateUserRegistered;
    }

    public long getYearsUser() {
        return yearsUser;
    }

    public void setYearsUser(long yearsUser) {
        this.yearsUser = yearsUser;
    }

    public void setYearsUserH(String birthday) {
        String[] birthDaySplit = birthday.split("-");
        this.yearsUser = (int)ChronoUnit.YEARS.between(LocalDate.of(Integer.parseInt(birthDaySplit[0]), Integer.parseInt(birthDaySplit[1]), Integer.parseInt(birthDaySplit[2])), LocalDate.now());

    }

    public void setAge(String birthday) {
    }

    public Map<String, Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(Map<String, Hero> heroes) {
        this.heroes = heroes;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    @JsonProperty("team")
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @JsonProperty("gender")
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
