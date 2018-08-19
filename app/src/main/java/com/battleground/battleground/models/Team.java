package com.battleground.battleground.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Team {
    SUPERHEROES, SUPERVILLAINS, NOT_SET;

    Team(){}

    String value;

    @JsonValue
    public String getValue(){
        return this.name();
    }

    @JsonCreator
    public static Team fromValue(String jobLifecycleString){
        for (Team l : Team.values()){
            if (l.name().equals(jobLifecycleString)){
                return l;
            }
        }
        throw new IllegalArgumentException("Invalid jobLifecycle code: " + jobLifecycleString);
    }
}
