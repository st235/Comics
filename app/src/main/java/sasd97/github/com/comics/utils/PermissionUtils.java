package sasd97.github.com.comics.utils;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Alexadner Dadukin on 2/27/2017.
 */

public class PermissionUtils {

    private PermissionUtils() {}

    public static void request(int id, Activity activity, String... options) {
        ActivityCompat.requestPermissions(activity, options, id);
    }

    public void handle() {

    }
}
