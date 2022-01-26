package com.example.runwith.domain;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    public static final String PREFERENCE_NAME="id";
    private static User userInstance;
    private Context mContext;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;

    public static synchronized User init(Context context){
        if(userInstance == null)
            userInstance = new User(context);
        return userInstance;
    }

    private User(Context context) {
        mContext = context;
        prefs = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE );
        prefsEditor = prefs.edit();
    }

    public static String read(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    public static void write(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }
}
