package com.example.weather_forecast;

import com.example.weather_forecast.model.WeatherData;

public class CallbackUtils {
    public interface weatherDataReturn {
        void Callback(WeatherData weatherData);
    }
}
