package sasd97.github.com.comics.repositories;

import android.support.annotation.NonNull;

import sasd97.github.com.comics.models.UserModel;
import sasd97.github.com.comics.storage.Storage;

/**
 * Created by alexander on 29/04/2017.
 */

public class AccountRepository {

    private static final String IS_STORED_KEY = "is.user.stored";
    private static final String USER_ID_KEY = "user.id";
    private static final String USER_EMAIL_KEY = "user.email";
    private static final String USER_ACCESS_TOKEN_KEY = "user.token";
    private static final String USER_IS_VERIFIED_KEY = "user.is.verified";
    private static final String USER_COMICS_LIST_KEY = "user.comics";

    private UserModel user;
    private Storage<String> storage;

    public AccountRepository(@NonNull Storage<String> storage) {
        this.storage = storage;
    }

    public void save(UserModel user) {
        this.user = user;

        storage
                .put(IS_STORED_KEY, true)
                .put(USER_ID_KEY, user.getId())
                .put(USER_EMAIL_KEY, user.getEmail())
                .put(USER_ACCESS_TOKEN_KEY, user.getAccessToken())
                .put(USER_IS_VERIFIED_KEY, user.isVerified());
    }

    public boolean restore() {
        if (!storage.getBoolean(IS_STORED_KEY, false)) return false;

        user = new UserModel();
        user.setId(storage.getString(USER_ID_KEY, null));
        user.setEmail(storage.getString(USER_EMAIL_KEY, null));
        user.setAccessToken(storage.getString(USER_ACCESS_TOKEN_KEY, null));
        user.setVerified(storage.getBoolean(USER_IS_VERIFIED_KEY, false));

        return true;
    }

    public void remove() {
        storage.clear();
    }

    public UserModel getUser() {
        return user;
    }

    public boolean isExists() {
        return storage.getBoolean(IS_STORED_KEY, false);
    }
}
