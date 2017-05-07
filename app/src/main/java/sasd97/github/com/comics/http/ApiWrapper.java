package sasd97.github.com.comics.http;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import sasd97.github.com.comics.models.BaseResponseModel;
import sasd97.github.com.comics.models.ComicsModel;
import sasd97.github.com.comics.models.UserModel;

import static sasd97.github.com.comics.constants.HttpConstants.LIMIT_PAGINATION_DEFAULT_VALUE;

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

    public static Call<?> login(@NonNull String email,
                                @NonNull String password,
                                @NonNull ApiListener<BaseResponseModel<UserModel>> callback) {
        Call<BaseResponseModel<UserModel>> loginUser = ApiObserver.api().loginUser(email, password);
        loginUser.enqueue(new ApiHandler<>(callback));
        return loginUser;
    }

    public static Call<?> obtainComics(@NonNull String comicsId,
                                       @NonNull ApiListener<BaseResponseModel<ComicsModel>> callback) {
        Call<BaseResponseModel<ComicsModel>> obtainComics = ApiObserver.api().obtainComics(comicsId);
        obtainComics.enqueue(new ApiHandler<>(callback));
        return obtainComics;
    }

    public static Call<?> obtainAllComics(int offset,
                                          @NonNull ApiListener<BaseResponseModel<List<ComicsModel>>> callback) {
        Call<BaseResponseModel<List<ComicsModel>>> obtainComics = ApiObserver.api().obtainComicsList(LIMIT_PAGINATION_DEFAULT_VALUE, offset);
        obtainComics.enqueue(new ApiHandler<>(callback));
        return obtainComics;
    }
}
