package sasd97.github.com.comics.http;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import sasd97.github.com.comics.models.BaseResponseModel;
import sasd97.github.com.comics.models.ErrorModel;
import sasd97.github.com.comics.models.ErrorResponseModel;

/**
 * Created by alexander on 30/04/2017.
 */

public class ApiHandler<T> implements Callback<T> {

    private static final String TAG = ApiHandler.class.getCanonicalName();

    private ApiListener<T> listener;

    public ApiHandler(ApiListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful() && response.body() != null) {
            listener.onSuccess(response.body());
        } else {
            ErrorModel error = parseError(response);
            String message = String.format("There was an error:\nmethod: %1$s\nurl: %2$s\nwith message: %3$s",
                    call.request().method(), call.request().url(), error.toString());
            Log.e(TAG, message);
            listener.onError(error);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String message = String.format("There was an error:\nmethod: %1$s\nurl: %2$s",
                call.request().method(), call.request().url());
        t.printStackTrace();
        Log.e(TAG, message);
        listener.onError(ErrorModel.UNKNOWN_ERROR);
    }

    private static ErrorModel parseError(Response<?> response) {
        if (response.errorBody() == null) {
            return ErrorModel.UNKNOWN_ERROR;
        }

        Converter<ResponseBody, ErrorResponseModel> converter =
                ApiObserver
                        .retrofit()
                        .responseBodyConverter(ErrorResponseModel.class, new Annotation[0]);

        try {
            ErrorModel errorModel = converter.convert(response.errorBody()).getError();
            return  errorModel == null ? ErrorModel.UNKNOWN_ERROR : errorModel;
        } catch (IOException e) {
            return ErrorModel.UNKNOWN_ERROR;
        }
    }
}
