package com.example.weather_forecast.tools;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class MultilingualControlCenter {
    public static void setLocaleForMainAppCompat(Context context, String languageCode) {
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        Locale newLocale;
        String delimiter = languageCode.contains("_") ? "_" : "-";
        String[] langCountry = languageCode.split(delimiter);
        if (langCountry.length == 2) {
            newLocale = new Locale(langCountry[0], langCountry[1]);
        } else {
            newLocale = new Locale(languageCode);
        }
        config.setLocale(newLocale);
        context.createConfigurationContext(config);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}
