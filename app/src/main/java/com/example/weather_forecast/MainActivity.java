package com.example.weather_forecast;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_forecast.api.CloudUtils;
import com.example.weather_forecast.api.HttpReturn;
import com.example.weather_forecast.model.ElementValue;
import com.example.weather_forecast.model.Location;
import com.example.weather_forecast.model.Locations;
import com.example.weather_forecast.model.Time;
import com.example.weather_forecast.model.WeatherData;
import com.example.weather_forecast.model.WeatherElement;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView countyCity,area,date,temp,weather,comfort,windDirection,windSpeed,humidity,rainfall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hsinchuWeatherForecast(new CallbackUtils.weatherDataReturn() {
            @Override
            public void Callback(WeatherData weatherData) {
                parse(weatherData);
            }
        });
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

    private void hsinchuWeatherForecast(CallbackUtils.weatherDataReturn weatherDataReturn){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpReturn httpReturn = CloudUtils.iCloudUtils.hsinchuWeatherForecast("東區",timeFromFormat(),timeToFormat());
                boolean isSuccess = Boolean.parseBoolean(httpReturn.success);
                if(isSuccess){
                    Gson gson = new Gson();
                    String records = gson.toJson(httpReturn.records);
                    WeatherData weatherData = gson.fromJson(records, WeatherData.class);
                    weatherDataReturn.Callback(weatherData);
                }
            }
        }).start();
    }

    private String timeFromFormat(){
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateString = dateFormat.format(date);
        return dateString;
    }

    private String timeToFormat(){
        long timestamp = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateString = dateFormat.format(calendar.getTime());
        return dateString;
    }

    private void parse(WeatherData weatherData){
        List<Locations> locations = weatherData.getLocations();
        if(!locations.isEmpty()){
            for (Locations location : locations) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    countyCity.setText(location.getLocationsName());
                });

                List<Location> weatherElements = location.getLocation();
                for (Location weatherElement : weatherElements){
                    new Handler(Looper.getMainLooper()).post(() -> {
                        area.setText(weatherElement.getElementName());
                    });

                    List<WeatherElement> weathers = weatherElement.getWeatherElement();
                    for(WeatherElement weather : weathers){
                        List<Time> times = weather.getTime();
                        if(!times.isEmpty()){
                            Time time = times.get(0);
                            List<ElementValue> elementValues = time.getElementValue();
                            for(ElementValue elementValue : elementValues){
                                stringCutting(elementValue.getValue());
                            }
                        } else {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                Toast.makeText(MainActivity.this, "天氣預報沒有資料", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                }
            }
        } else {
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(MainActivity.this, "天氣預報沒有資料", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void stringCutting(String values){
        String[] value = values.split("。");
        new Handler(Looper.getMainLooper()).post(() -> {
            weather.setText(value[0]);
            rainfall.setText(value[1].replace("降雨機率",""));
            temp.setText(value[2].replace("溫度",""));
            comfort.setText(value[3]);
            String[] wind = value[4].split("\\s平均風速");
            windDirection.setText(wind[0]);
            windSpeed.setText(String.format("平均風速%s",wind[1]));
            humidity.setText(value[5].replace("相對濕度",""));
            date.setText(nowDate());
        });
    }

    private String nowDate(){
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH點mm分");
        String dateString = dateFormat.format(date);
        return dateString;
    }
}