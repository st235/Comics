package sasd97.github.com.comics.http;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sasd97.github.com.comics.models.BaseResponseModel;
import sasd97.github.com.comics.models.UserModel;

/**
 * Created by alexander on 14.03.17.
 */

public interface ComicsApi {

    @POST("/register")
    @FormUrlEncoded
    Call<BaseResponseModel<UserModel>> registerUser(@Field("email") String email,
                                                    @Field("password") String password);

    @POST("/login")
    @FormUrlEncoded
    Call<BaseResponseModel<UserModel>> loginUser(@Field("email") String email,
                                                 @Field("password") String password);
}
