package sasd97.github.com.comics.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 30/04/2017.
 */

public class ErrorResponseModel {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("error")
    @Expose
    private ErrorModel error;

    public boolean isSuccess() {
        return success;
    }

    public ErrorModel getError() {
        return error;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorResponseModel{");
        sb.append("success=").append(success);
        sb.append(", error=").append(error);
        sb.append('}');
        return sb.toString();
    }
}
