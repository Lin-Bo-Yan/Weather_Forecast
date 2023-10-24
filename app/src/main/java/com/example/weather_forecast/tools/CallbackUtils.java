package com.example.weather_forecast.tools;

import com.example.weather_forecast.model.WeatherData;

public class CallbackUtils {
    public interface weatherDataReturn {
        void Callback(WeatherData weatherData);
    }

    public interface noReturn {
        void Callback();
    }
}
