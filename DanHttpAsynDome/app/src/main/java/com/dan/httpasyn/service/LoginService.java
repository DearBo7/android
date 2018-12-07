package com.dan.httpasyn.service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dan on 2018/10/18 11:17
 */
public class LoginService {

    private static final String FileKey = "user_login";

    private SharedPreferences.Editor edit;

    private SharedPreferences sharedPreferences;

    public LoginService(Context context) {
        this(context, FileKey);
    }

    public LoginService(Context context, String fileKey) {
        this.sharedPreferences = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        this.edit = this.sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public Boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean saveUser(String key, Object param) {
        if (param instanceof Integer) {
            return edit.putInt(key, Integer.parseInt(String.valueOf(param))).commit();
        } else if (param instanceof Double) {
            return edit.putFloat(key, Float.parseFloat(String.valueOf(param))).commit();
        } else if (param instanceof Float) {
            return edit.putFloat(key, Float.parseFloat(String.valueOf(param))).commit();
        } else if (param instanceof Long) {
            return edit.putLong(key, Long.parseLong(String.valueOf(param))).commit();
        } else if (param instanceof Boolean) {
            return edit.putBoolean(key, Boolean.parseBoolean(String.valueOf(param))).commit();
        }
        return edit.putString(key, String.valueOf(param)).commit();
    }

    public boolean deleteUser(String key) {
        return edit.remove(key).commit();
    }

    public boolean clearUser() {

        return edit.clear().commit();
    }
}
