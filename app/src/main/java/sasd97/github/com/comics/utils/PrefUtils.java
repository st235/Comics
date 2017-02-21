package sasd97.github.com.comics.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Alexander Dadukin on 21.04.2016.
 */

public final class PrefUtils {

    private static String TAG = "SHARED_PREFS";
    private static String APP_PREFERENCES = "COMICS_PREFS";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private PrefUtils() {}

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static <T> void put(String key, T value) {

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        }

        Log.d(TAG, "Putted key: " + key + " value: " + value);
        editor.apply();
    }

    public static SharedPreferences get() {
        return sharedPreferences;
    }

    public static SharedPreferences.Editor edit() {
        return editor;
    }
}
