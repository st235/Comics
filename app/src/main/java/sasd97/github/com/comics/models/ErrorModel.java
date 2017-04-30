package sasd97.github.com.comics.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexander on 30/04/2017.
 */

public class ErrorModel {

    public static final ErrorModel UNKNOWN_ERROR = new ErrorModel(500, "Unknown error");

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message;

    public ErrorModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorModel{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
