package sasd97.github.com.comics.http;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alexander on 14.03.17.
 */

public interface ComicsApi {

    @POST("/Users")
    @FormUrlEncoded
    Call<?> registerUser(@Field("email") String email,
                         @Field("password") String password);
}
