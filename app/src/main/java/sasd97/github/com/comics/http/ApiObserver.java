package sasd97.github.com.comics.http;

import retrofit2.Retrofit;

import static sasd97.github.com.comics.constants.HttpConstants.BASE_API_URL;

/**
 * Created by alexander on 14.03.17.
 */

public class ApiObserver {

    private static ApiObserver apiObserver;
    private static ComicsApi comicsApi;

    private ApiObserver() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .build();

        comicsApi = retrofit.create(ComicsApi.class);
    }

    public static ApiObserver init() {
        if (apiObserver == null) {
            apiObserver = new ApiObserver();
        }

        return apiObserver;
    }

    public static ComicsApi api() {
        return comicsApi;
    }
}
