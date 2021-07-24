package com.example.steps2;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    public static void save(String key,int value){
        Context context = MyApp.getContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences("Steps2",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void save(String key,String value){
        Context context = MyApp.getContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences("Steps2",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(key, value);
        editor.commit();
    }

    public static String loadString(String key,String defaultValue){
        Context context=MyApp.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Steps2",context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);

    }

    public static String loadString(String key){
        return loadString(key,null);

    }

    public static int loadInt(String key,int defaultValue){
        Context context=MyApp.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Steps2",context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);

    }

    public static int loadInt(String key){
        return loadInt(key,0);

    }
}
