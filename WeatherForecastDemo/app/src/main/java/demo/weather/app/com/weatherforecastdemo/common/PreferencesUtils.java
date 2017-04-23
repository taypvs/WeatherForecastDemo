package demo.weather.app.com.weatherforecastdemo.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferencesUtils {

    public static String SAVE_LAT = "SAVE_LAT";
    public static String SAVE_LON = "SAVE_LON";
    public static String SAVE_CUSTOM = "SAVE_CUSTOM";

    public static void saveString(Context context, String key, String detail){
        //Creating a shared preference
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(key, detail);
        prefsEditor.commit();
    }

    public static String getString(Context context, String key){
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString(key, "");
    }

    public static void saveFloat(Context context, String key, float data){
        //Creating a shared preference
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor prefsEditor = mPrefs.edit();
        prefsEditor.putFloat(key, data);
        prefsEditor.commit();
    }

    public static float getFloat(Context context, String key){
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getFloat(key, 0);
    }

    public static void clearKeyPreferences(Context context, String key){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().remove(key).commit();
    }

}