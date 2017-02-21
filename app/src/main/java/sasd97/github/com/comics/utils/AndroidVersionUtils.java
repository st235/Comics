package sasd97.github.com.comics.utils;

import android.os.Build;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public final class AndroidVersionUtils {

    private AndroidVersionUtils() {}

    public static boolean isIceCreamSandwitchOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean isHoneycombOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isHoneycombMR1orLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean isJellyBeanMR1orLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isKitKatOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isLollipopOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isMarshmallowOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isNougatOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
