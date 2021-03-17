package com.example.noteapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveInShown(){
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isShown() {
       return preferences.getBoolean("isSown", false);
    }
}
