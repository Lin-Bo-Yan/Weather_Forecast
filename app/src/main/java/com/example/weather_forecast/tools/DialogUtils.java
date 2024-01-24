package com.example.weather_forecast.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import com.example.weather_forecast.R;

import java.util.ArrayList;
import java.util.List;

public class DialogUtils {

    public static void showLanguageSelection(Activity activity, ArrayAdapter<String> arrayAdapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.change_language));
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedItem = arrayAdapter.getItem(which);
                String languageRegionCode = StringUtils.languageRegionCode(activity,selectedItem);
                MultilingualControlCenter.setLocaleForMainAppCompat(activity,languageRegionCode);
                activity.recreate();
            }
        });
        builder.setNegativeButton(activity.getString(R.string.cancel_button), null);
        builder.show();
    }

    // 按鈕可調，按鈕有後續動作，按空白處不可關閉Dialog
    public static void showDialogWebMessage(Activity activity, String title, List<String> buttons, List<CallbackUtils.noReturn> callbacks) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle(title);

                List<String> translatedButtons = new ArrayList<>();
                for (String button : buttons) {
                    String translatedButton = translateButton(button);
                    translatedButtons.add(translatedButton);
                }

                if(translatedButtons.size() > 0){
                    builder.setPositiveButton(translatedButtons.get(0), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            callbacks.get(0).Callback();
                        }
                    });
                }

                if(translatedButtons.size() > 1){
                    builder.setNegativeButton(translatedButtons.get(1), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            callbacks.get(1).Callback();
                        }
                    });
                }

                if(translatedButtons.size() > 2){
                    builder.setNeutralButton(translatedButtons.get(2), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            callbacks.get(2).Callback();
                        }
                    });
                }
                builder.setCancelable(false)
                        .create()
                        .show();
            }
        });
    }

    public static void showDialogWebMessage(Activity activity, String title, String text, List<String> buttons, List<CallbackUtils.noReturn> callbacks) {
        activity.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(text);

            List<String> translatedButtons = new ArrayList<>();
            for (String button : buttons) {
                String translatedButton = translateButton(button);
                translatedButtons.add(translatedButton);
            }

            if(translatedButtons.size() > 0){
                builder.setPositiveButton(translatedButtons.get(0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(0).Callback();
                    }
                });
            }

            if(translatedButtons.size() > 1){
                builder.setNegativeButton(translatedButtons.get(1), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(1).Callback();
                    }
                });
            }

            if(translatedButtons.size() > 2){
                builder.setNeutralButton(translatedButtons.get(2), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(2).Callback();
                    }
                });
            }
            builder.setCancelable(false).create().show();
        });
    }

    // 按鈕可調，按鈕有後續動作，按空白處可關閉Dialog
    public static void showDialogCancelable(Activity activity, String title, List<String> buttons, List<CallbackUtils.noReturn> callbacks) {
        activity.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setMessage(title);

            List<String> translatedButtons = new ArrayList<>();
            for (String button : buttons) {
                String translatedButton = translateButton(button);
                translatedButtons.add(translatedButton);
            }

            if(translatedButtons.size() > 0){
                builder.setPositiveButton(translatedButtons.get(0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(0).Callback();
                    }
                });
            }

            if(translatedButtons.size() > 1){
                builder.setNegativeButton(translatedButtons.get(1), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(1).Callback();
                    }
                });
            }

            if(translatedButtons.size() > 2){
                builder.setNeutralButton(translatedButtons.get(2), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(2).Callback();
                    }
                });
            }
            builder.create().show();
        });
    }

    public static void showDialogCancelable(Activity activity, String title, String text, List<String> buttons, List<CallbackUtils.noReturn> callbacks) {
        activity.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(text);

            List<String> translatedButtons = new ArrayList<>();
            for (String button : buttons) {
                String translatedButton = translateButton(button);
                translatedButtons.add(translatedButton);
            }

            if(translatedButtons.size() > 0){
                builder.setPositiveButton(translatedButtons.get(0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(0).Callback();
                    }
                });
            }

            if(translatedButtons.size() > 1){
                builder.setNegativeButton(translatedButtons.get(1), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(1).Callback();
                    }
                });
            }

            if(translatedButtons.size() > 2){
                builder.setNeutralButton(translatedButtons.get(2), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callbacks.get(2).Callback();
                    }
                });
            }
            builder.create().show();
        });
    }

    private static String translateButton(String button) {
        switch (button) {
            case "logout":
                return "登出";
            case "ok":
                return "確定";
            case "cancel":
                return "取消";
            default:
                return button;
        }
    }

}
