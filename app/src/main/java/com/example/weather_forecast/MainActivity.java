package com.example.weather_forecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.example.weather_forecast.tools.CallbackUtils;
import com.example.weather_forecast.tools.DialogUtils;
import com.example.weather_forecast.tools.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView countyCity,area,date,temp,weather,comfort,windDirection,windSpeed,humidity,rainfall;
    private AppCompatImageView ic_multilingual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextView();
        ic_multilingual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] languageSelector = new String[]{"繁體中文", "簡體中文", "English"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        languageSelector);
                DialogUtils.showLanguageSelection(MainActivity.this,arrayAdapter);

                //自定義訊息框，多個按鈕
                Gson gson = new Gson();
                String buttonString = "[\"ok\",\"logout\",\"按鈕3\"]";
                Type listType = new TypeToken<List<String>>(){}.getType();
                List<String> buttons = gson.fromJson(buttonString, listType);
                List<CallbackUtils.noReturn> callbacks = new ArrayList<>();
                DialogUtils.showDialogWebMessage(MainActivity.this,"標題",buttons,callbacks);
                for (int i = 0; i < buttons.size(); i++) {
                    // 建立一個有效最終變數的副本
                    final int buttonIndex = i;
                    CallbackUtils.noReturn callback = new CallbackUtils.noReturn() {
                        @Override
                        public void Callback() {
                            String button = buttons.get(buttonIndex);
                            StringUtils.HaoLog("訊息= " + button);
                        }
                    };
                    callbacks.add(callback);
                }
            }
        });
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
        ic_multilingual = findViewById(R.id.ic_multilingual);
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

    private String nowDate() {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        return dateFormat.format(date);
    }
}