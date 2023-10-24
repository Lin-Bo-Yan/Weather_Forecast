package com.example.weather_forecast.tools;

import android.app.Activity;
import android.util.Log;

import com.example.weather_forecast.R;

public class StringUtils {
    final static int MAX_LOG = 800;

    public static void HaoLog(String data) {
        HaoLog(data, 4);
    }

    public static void HaoLog(String data, int showC) {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if(stes.length > showC){
            showC =stes.length -1;
        }
        if (stes != null && stes.length > showC) {
            if (stes[showC].getFileName().equals("MainAppCompatActivity.java")){
                showC++;
            }
            if (stes[showC].getFileName().equals("MessageBaseActivity.java")){
                showC++;
            }
            String tag = "HaoLog";
            tag += " (" + stes[showC].getFileName() + ":" + stes[showC].getLineNumber() + ") ";
            tag += stes[showC].getMethodName() + " Thread=" + Thread.currentThread().getName() + "　 ";
            if (data == null){
                Log.d(tag, "null");
            } else if (data.length() < MAX_LOG){
                Log.d(tag, data);
            } else {
                int p = data.length() / MAX_LOG;
                if (data.length() % MAX_LOG == 0) {
                    for (int i = 0; i < p; i++) {
                        Log.d(tag, data.substring(i * MAX_LOG, (i + 1) * MAX_LOG));
                    }
                } else {
                    for (int i = 0; i < p; i++) {
                        Log.d(tag, data.substring(i * MAX_LOG, (i + 1) * MAX_LOG));
                    }
                    Log.d(tag, data.substring(p * MAX_LOG));
                }
            }
        }
    }

    public static String languageRegionCode(Activity activity, String language){
        if (language.equals(activity.getString(R.string.traditional_chinese))) {
            return "zh-TW";
        } else if (language.equals(activity.getString(R.string.simplified_chinese))) {
            return "zh-CN";
        } else if (language.equals(activity.getString(R.string.english_language))) {
            return "en";
        } else {
            return "zh-TW";
        }
    }
}
