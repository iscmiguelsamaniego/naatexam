package org.samtech.naatexamen.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;

@SuppressLint("StaticFieldLeak")
public class PreferencesUtils {
    private static final String PREFS_NAME = "ibilletePreferences";
    private static PreferencesUtils singleton;
    private Context context;

    private PreferencesUtils(Context context) {
        this.context = context;
    }


    public static PreferencesUtils with(Context context) {
        if (singleton == null) {
            synchronized (PreferencesUtils.class) {
                if (singleton == null) {
                    if (context == null) {
                        throw new IllegalArgumentException("El contenido no debe estar vacio");
                    }
                    singleton = new PreferencesUtils(context);
                    singleton.context = context.getApplicationContext();
                }
            }
        }
        return singleton;
    }

    public String getString(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(key, "");
    }

    public boolean getBoolean(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getBoolean(key, true);
    }

    public Object getObject(String key, Class<?> fromClass) {
        try {
            String json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(key, null);
            if (json != null) {
                Gson gson = new Gson();
                return gson.fromJson(json, fromClass);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void putString(String key, String value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .apply();
    }

    public void putBoolean(String key, Boolean value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public void removePreference(String key) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(key)
                .apply();
    }

    public void saveObjectAsPrefs(Object object, String paramKey) {
        Gson gson = new Gson();
        PreferencesUtils.with(context).putString(paramKey, gson.toJson(object));
    }

    public Object getObjectAsPrefs(String paramKey,
                                   Class paramClass) {
        Gson gson = new Gson();
        return gson.fromJson(PreferencesUtils.with(context).
                getString(paramKey), paramClass);
    }

}
