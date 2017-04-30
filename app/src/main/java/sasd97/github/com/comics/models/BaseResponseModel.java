package sasd97.github.com.comics.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 30/04/2017.
 */

public class BaseResponseModel<T> {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("response")
    @Expose
    private T response;

    public boolean isSuccess() {
        return success;
    }

    public T getResponse() {
        return response;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseResponseModel{");
        sb.append("success=").append(success);
        sb.append(", response=").append(response);
        sb.append('}');
        return sb.toString();
    }
}
