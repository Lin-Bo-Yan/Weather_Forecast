package com.example.weather_forecast.model;

import java.util.List;

public class Time{
    private String startTime;
    private String endTime;
    private List<ElementValue> elementValue;

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public List<ElementValue> getElementValue() {
        return elementValue;
    }

}
