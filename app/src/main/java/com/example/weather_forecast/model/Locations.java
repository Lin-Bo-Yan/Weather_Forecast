package com.example.weather_forecast.model;

import java.util.List;

public class Locations {
    private String datasetDescription;
    private String locationsName;
    private String dataid;
    private List<Location> location;

    public String getDatasetDescription() {
        return datasetDescription;
    }

    public String getLocationsName() {
        return locationsName;
    }

    public String getDataid() {
        return dataid;
    }

    public List<Location> getLocation() {
        return location;
    }
}
