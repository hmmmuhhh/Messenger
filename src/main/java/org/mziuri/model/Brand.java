package org.mziuri.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Brand {

    MAZDA("Mazda"), HONDA("Honda");

    private final String name;

    Brand(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
