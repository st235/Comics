package sasd97.github.com.comics.http;

import sasd97.github.com.comics.models.ErrorModel;

/**
 * Created by alexander on 30/04/2017.
 */

public interface ApiListener<T> {
    void onSuccess(T t);
    void onError(ErrorModel errorModel);
}
