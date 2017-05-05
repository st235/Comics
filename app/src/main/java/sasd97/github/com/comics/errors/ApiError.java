package sasd97.github.com.comics.errors;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import sasd97.github.com.comics.R;

/**
 * Created by alexander on 05/05/2017.
 */

public enum ApiError {

    INTERNAL_SERVER_ERROR(1, R.string.all_unhandled_error_description),
    EMAIL_IN_USE_ERROR(2, R.string.all_email_in_use_error_description),
    BAD_REQUEST_ERROR(3, R.string.all_unhandled_error_description),
    UNAUTHORIZED(4, R.string.all_unauthorized_error_description),
    ALREADY_CONFIRMED(5, R.string.all_unhandled_error_description),
    USER_NOT_FOUND(6, R.string.all_unhandled_error_description),
    EMAIL_NOT_CONFIRMED(7, R.string.all_email_not_confirmed_error_description),
    COMICS_NOT_FOUND(8, R.string.all_unhandled_error_description),
    COMICSES_NOT_FOUND(9, R.string.all_unhandled_error_description);

    private int errorCode;
    private int explanationTextError;

    ApiError(int errorCode, @StringRes int explanationTextError) {
        this.errorCode = errorCode;
        this.explanationTextError = explanationTextError;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getExplanationTextError() {
        return explanationTextError;
    }

    public static ApiError find(int errorCode) {
        for (ApiError error: values()) {
            if (errorCode == error.getErrorCode()) return error;
        }
        return INTERNAL_SERVER_ERROR;
    }
}
