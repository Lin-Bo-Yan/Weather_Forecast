package com.example.weather_forecast.tools;

import android.app.Activity;
import com.example.weather_forecast.R;
import com.example.weather_forecast.api.HttpReturn;

public class StringUtils {
    final static int MAX_LOG = 800;

    public static void HaoLog(HttpReturn data) {
        HaoLog("httpReturn " + data.success + " " + data.result + " " + data.records, 5);
    }

    public static void HaoLog(String data, int showC) {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if(stes.length > showC){
            showC =stes.length -1;
        }
        if (stes.length > showC) {
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
                LogUtils.d(tag, "null");
            } else if (data.length() < MAX_LOG){
                LogUtils.d(tag, data);
            } else {
                int p = data.length() / MAX_LOG;
                if (data.length() % MAX_LOG == 0) {
                    for (int i = 0; i < p; i++) {
                        LogUtils.d(tag, data.substring(i * MAX_LOG, (i + 1) * MAX_LOG));
                    }
                } else {
                    for (int i = 0; i < p; i++) {
                        LogUtils.d(tag, data.substring(i * MAX_LOG, (i + 1) * MAX_LOG));
                    }
                    LogUtils.d(tag, data.substring(p * MAX_LOG));
                }
            }
        }
    }
    
    static public void HaoLog(String data) {
        HaoLog(data, 4);
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

    public static boolean version(String appVersion, String dbVersion) {
        String[] appVersionParts = appVersion.split("\\.");
        String[] dbVersionParts = dbVersion.split("\\.");
        int length = Math.min(appVersionParts.length, dbVersionParts.length);
        for (int i = 0; i < length; i++) {
            int appPart = Integer.parseInt(appVersionParts[i]);
            int dbPart = Integer.parseInt(dbVersionParts[i]);
            if (dbPart > appPart) {
                return true;
            } else if (dbPart < appPart) {
                return false;
            }
        }
        if (dbVersionParts.length > appVersionParts.length) {
            return true;
        }
        return false;
    }
}
