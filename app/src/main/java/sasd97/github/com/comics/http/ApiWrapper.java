package sasd97.github.com.comics.http;

import android.support.annotation.NonNull;

import retrofit2.Call;
import sasd97.github.com.comics.models.BaseResponseModel;
import sasd97.github.com.comics.models.UserModel;

/**
 * Created by alexander on 29/04/2017.
 */

public class ApiWrapper {

    private ApiWrapper() {
    }

    public static Call<?> register(@NonNull String email,
                                   @NonNull String password,
                                   @NonNull ApiListener<BaseResponseModel<UserModel>> callback) {
        Call<BaseResponseModel<UserModel>> registerUser = ApiObserver.api().registerUser(email, password);
        registerUser.enqueue(new ApiHandler<>(callback));
        return registerUser;
    }
}
