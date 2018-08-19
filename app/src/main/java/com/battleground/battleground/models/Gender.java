package com.battleground.battleground.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE, FEMALE;

    Gender(){}

    String value;

    @JsonValue
    public String getValue(){
        return this.name();
    }

    @JsonCreator
    public static Gender fromValue(String jobLifecycleString){
        for (Gender l : Gender.values()){
            if (l.name().equals(jobLifecycleString)){
                return l;
            }
        }
        throw new IllegalArgumentException("Invalid jobLifecycle code: " + jobLifecycleString);
    }
}
