package com.example.weather_forecast.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CloudUtils implements ICloudUtils {

    public static ICloudUtils iCloudUtils = new CloudUtils();

    @Override
    public HttpReturn hsinchuWeatherForecast(String area, String timeFrom, String timeTo) {
        String authorization = "CWB-E090D0FF-2372-4403-A725-F032945C25C8";
        String elementName = "WeatherDescription";
        String url = String.format("https://opendata.cwa.gov.tw/api/v1/rest/datastore/F-D0047-053?Authorization=%s&locationName=%s&elementName=%s&limit=1&offset=0&sort=time&timeFrom=%s&timeTo=%s&format=JSON",authorization,area,elementName,timeFrom,timeTo);
        Request.Builder request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json");
        return gethttpReturn(request,15);
    }

    public HttpReturn gethttpReturn(Request.Builder request, int timeoutInSeconds) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .build();
        try {
            Response response = client.newCall(request.build()).execute();
            if (response.code() == 200) {
                String body = response.body().string();
                HttpReturn httpReturn = new Gson().fromJson(body, HttpReturn.class);
                if (httpReturn != null) {
                    return httpReturn;
                }
            }
        } catch (IOException | JsonSyntaxException | IllegalStateException e) {
            if(e instanceof java.net.SocketTimeoutException){

            }
            e.printStackTrace();
        }
        return new HttpReturn();
    }
}
