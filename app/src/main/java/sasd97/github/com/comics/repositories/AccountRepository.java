package sasd97.github.com.comics.repositories;

import android.support.annotation.NonNull;

import sasd97.github.com.comics.storage.Storage;

/**
 * Created by alexander on 29/04/2017.
 */

public class AccountRepository {

    private static final String IS_STORED_KEY = "is.user.stored";
    private static final String USER_ID_KEY = "is.user.stored";
    private static final String USER_EMAIL_KEY = "is.user.stored";
    private static final String USER_ACCESS_TOKEN_KEY = "is.user.stored";
    private static final String USER_COMICS_LIST_KEY = "is.user.stored";

    private Storage<String> storage;

    public AccountRepository(@NonNull Storage<String> storage) {
        this.storage = storage;
    }

    public boolean restore() {

    }
}
