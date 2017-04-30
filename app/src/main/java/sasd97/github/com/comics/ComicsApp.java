package sasd97.github.com.comics;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import sasd97.github.com.comics.database.DatabaseConnector;
import sasd97.github.com.comics.http.ApiObserver;
import sasd97.github.com.comics.repositories.AccountRepository;
import sasd97.github.com.comics.storage.PrefsStorage;

/**
 * Created by Alexander Dadukin on 2/5/2017.
 */

public class ComicsApp extends Application {

    private static SQLiteDatabase database;
    private static PrefsStorage prefsStorage;
    private static AccountRepository accountRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        ApiObserver.init();

        prefsStorage = new PrefsStorage(this);

        accountRepository = new AccountRepository(prefsStorage);
        accountRepository.restore();

        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        database = databaseConnector.getWritableDatabase();
    }

    public static SQLiteDatabase db() {
        return database;
    }

    public static PrefsStorage prefs() {
        return prefsStorage;
    }

    public static AccountRepository account() {
        return accountRepository;
    }
}
