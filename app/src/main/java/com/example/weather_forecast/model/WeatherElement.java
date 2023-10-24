package com.example.weather_forecast.model;

import java.util.List;

public class WeatherElement {
    private String elementName;
    private String description;
    private List<Time> time;

    public String getElementName() {
        return elementName;
    }

    public String getDescription() {
        return description;
    }

    public List<Time> getTime() {
        return time;
    }

}
