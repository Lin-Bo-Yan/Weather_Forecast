package com.example.weather_forecast.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.example.weather_forecast.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogUtils {
    public static final String FOLDER_FILES = "files";
    private static final String NEW_LINE = System.getProperty("line.separator");
    public static boolean mLogcatAppender = BuildConfig.DEBUG;
    public static File mLogFile = null;


    public static void i(String TAG, String message) {
        appendLog(TAG + " : " + message);
        if (mLogcatAppender) {
            android.util.Log.i(TAG, message);
        }
    }

    public static void d(String TAG, String message) {
        appendLog(TAG + " : " + message);
        if (mLogcatAppender) {
            android.util.Log.d(TAG, message);
        }
    }

    public static void e(String TAG, String message) {
        appendLog(TAG + " : " + message);
        if (mLogcatAppender) {
            android.util.Log.e(TAG, message);
        }
    }

    public static void v(String TAG, String message) {
        appendLog(TAG + " : " + message);
        if (mLogcatAppender) {
            android.util.Log.v(TAG, message);
        }
    }

    public static void w(String TAG, String message) {
        appendLog(TAG + " : " + message);
        if (mLogcatAppender) {
            android.util.Log.w(TAG, message);
        }
    }

    public static void setContext(Context mContext) {
        if(mLogFile == null) {
            mLogFile = new File(getApplicationFolder(mContext, FOLDER_FILES) +
                    "/" + "weather_forecast_logs.log");
            if (!mLogFile.exists()) {
                try {
                    mLogFile.createNewFile();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            logDeviceInfo();
        }
    }

    private static synchronized void appendLog(String text) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        android.util.Log.d("TAG", "進行寫入");
        if (mLogFile != null) {
            try {
                final FileWriter fileOut = new FileWriter(mLogFile, true);
                fileOut.append(sdf.format(new Date()) + " : " + text + NEW_LINE);
                fileOut.close();
                android.util.Log.d("TAG", "寫入成功");
            } catch (final IOException e) {
                android.util.Log.d("TAG", "寫入失敗");
                e.printStackTrace();
            }
        } else {
            android.util.Log.d("TAG", "寫入失敗 mLogFile = null");
        }
    }

    private static void logDeviceInfo() {
        appendLog("Model : " + android.os.Build.MODEL);
        appendLog("Brand : " + android.os.Build.BRAND);
        appendLog("Product : " + android.os.Build.PRODUCT);
        appendLog("Device : " + android.os.Build.DEVICE);
        appendLog("Codename : " + android.os.Build.VERSION.CODENAME);
        appendLog("Release : " + android.os.Build.VERSION.RELEASE);
    }


    public static void saveLog(Activity activity) {
        List<String> buttons = new ArrayList<>();
        buttons.add("ok");
        List<CallbackUtils.noReturn> callbacks = new ArrayList<>();
        DialogUtils.showDialogWebMessage(activity,"","",buttons,callbacks);
        for (int i = 0; i < buttons.size(); i++) {
            final int buttonIndex = i;
            CallbackUtils.noReturn callback = new CallbackUtils.noReturn() {
                @Override
                public void Callback() {
                    String button = buttons.get(buttonIndex);
                    switch (button){
                        case "ok":
                            downloadDir();
                            break;
                    }
                }
            };
            callbacks.add(callback);
        }
    }

    private static void downloadDir(){
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File targetFile = new File(downloadDir, "log.log");
        try {
            FileInputStream fis = new FileInputStream(mLogFile);
            FileOutputStream fos = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
            fis.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getApplicationFolder(Context context, String subFolder) {
        File file = new File(context.getExternalFilesDir("").getParentFile(), subFolder);
        if(file != null) {
            if(!file.exists()){
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        return context.getExternalCacheDir().getAbsolutePath();
    }
}
