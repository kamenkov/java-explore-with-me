package ru.practicum.dto.location;

import javax.validation.constraints.NotNull;

public class LocationDto {

    @NotNull
    private float lat;

    @NotNull
    private float lon;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

}
