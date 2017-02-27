package sasd97.github.com.comics;

import android.app.Application;

import sasd97.github.com.comics.utils.PrefUtils;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public class ComicsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PrefUtils.init(this);
    }
}
