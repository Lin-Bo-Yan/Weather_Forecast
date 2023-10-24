package com.example.weather_forecast.api;

public interface ICloudUtils {
    /**
     * 新竹市未來兩天天氣預報
     */
    HttpReturn hsinchuWeatherForecast(String area, String timeFrom, String timeTo);
}
