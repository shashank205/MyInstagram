package com.example.myinstagram.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesStorage {

    private static final String SHARED_PREF_FILE = "com.example.myinstagram.storage";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, MODE_PRIVATE);
    }

    public void writeValue(String key, String value) {
        Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String readValue(String key, String defaultValue) {
       return this.sharedPreferences.getString(key, defaultValue);
    }

    public void writeValue(String key, int value) {
        Editor editor = this.sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int readValue(String key, int defaultValue) {
        return this.sharedPreferences.getInt(key, defaultValue);
    }

    public void writeValue(String key, float value) {
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float readValue(String key, float defaultValue) {
        return this.sharedPreferences.getFloat(key, defaultValue);
    }

    public void writeValue(String key, long value) {
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long readValue(String key, long defaultValue) {
        return this.sharedPreferences.getLong(key, defaultValue);
    }

    public void writeValue(String key, boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean readValue(String key, boolean defaultValue) {
        return this.sharedPreferences.getBoolean(key, defaultValue);
    }
}
