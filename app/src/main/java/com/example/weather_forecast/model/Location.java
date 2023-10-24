package com.example.weather_forecast.model;

import java.util.List;

public class Location {
    private String locationName;
    private String geocode;
    private String lat;
    private String lon;
    private List<WeatherElement> weatherElement;

    public String getElementName() {
        return locationName;
    }

    public String getGeocode() {
        return geocode;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public List<WeatherElement> getWeatherElement() {
        return weatherElement;
    }
}
