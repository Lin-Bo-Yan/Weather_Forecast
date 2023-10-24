package com.example.weather_forecast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView countyCity,area,date,temp,weather,comfort,windDirection,windSpeed,humidity,rainfall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextView();
    }

    private void initTextView(){
        countyCity = findViewById(R.id.countyCity);
        area = findViewById(R.id.area);
        date = findViewById(R.id.date);
        temp = findViewById(R.id.temp);
        weather = findViewById(R.id.weather);
        comfort = findViewById(R.id.comfort);
        windDirection = findViewById(R.id.windDirection);
        windSpeed = findViewById(R.id.windSpeed);
        humidity = findViewById(R.id.humidity);
        rainfall = findViewById(R.id.rainfall);
    }
}