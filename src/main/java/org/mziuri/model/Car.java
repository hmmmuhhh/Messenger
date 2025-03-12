package org.mziuri.model;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({ "year", "brand", "model" })
public class Car {

    private final Brand brand;
    private final String model;
    private final int year;

    @JsonCreator
    public Car(@JsonProperty("brand") Brand brand,
               @JsonProperty("model") String model,
               @JsonProperty("year") int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public Brand getBrand() {
        return brand;
    }

    @JsonGetter("model")
    public String carModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand=" + brand +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
