package ru.practicum.dto.location;

import javax.validation.constraints.NotNull;

public class LocationDto {

    @NotNull
    private Float lat;

    @NotNull
    private Float lon;

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

}
