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

    public User() {
    }

    public User(Gender gender, String birthday) {
        setGender(gender);
        setHeroes(new HashMap<String, Hero>());
        heroes.put("Spiderman", new Hero("Spiderman", Hero.SPIDERMAN_ATTACK, Hero.SPIDERMAN_DEFENCE));
        setClan(null);
        setTeam(Team.NOT_SET);
        setAge(birthday);
        setYearsUserH(birthday);
        setDateUserRegistered(LocalDate.now().toString());
        setGold(5000);
        setStatistics(new Statistics());
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
