package sasd97.github.com.comics;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import sasd97.github.com.comics.database.DatabaseConnector;
import sasd97.github.com.comics.http.ApiObserver;
import sasd97.github.com.comics.utils.PrefUtils;

/**
 * Created by Alexander Dadukin on 2/5/2017.
 */

public class ComicsApp extends Application {

    private static SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        ApiObserver.init();
        PrefUtils.init(this);

        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        database = databaseConnector.getWritableDatabase();
    }

    public static SQLiteDatabase db() {
        return database;
    }
}
