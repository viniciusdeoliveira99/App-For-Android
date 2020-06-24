package br.com.navegacao.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class PrefsUtils {

    public static boolean isCheckPushOn(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("PREF_CHECK_PUSH", false);
    }

    public static void adicionaDarkMode(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("DARK_MODE_ON", true);
        editor.apply();
    }
}
