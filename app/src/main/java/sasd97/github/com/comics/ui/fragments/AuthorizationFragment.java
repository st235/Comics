package sasd97.github.com.comics.ui.fragments;

import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.http.ApiListener;
import sasd97.github.com.comics.http.ApiWrapper;
import sasd97.github.com.comics.models.BaseResponseModel;
import sasd97.github.com.comics.models.ErrorModel;
import sasd97.github.com.comics.models.UserModel;
import sasd97.github.com.comics.ui.base.BaseFragment;

import static sasd97.github.com.comics.ComicsApp.account;

/**
 * Created by alexander on 16.03.17.
 */

public class AuthorizationFragment extends BaseFragment
        implements ApiListener<BaseResponseModel<UserModel>> {

    private static final String TAG = AuthorizationFragment.class.getCanonicalName();

    @BindView(R.id.input_email) EditText inputEmail;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.input_layout_email) TextInputLayout textLayoutEmail;
    @BindView(R.id.input_layout_password) TextInputLayout textLayoutPassword;

    @Override
    protected boolean isButterKnifeEnabled() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_authorization;
    }

    @OnClick(R.id.authorization_button)
    public void onAuthorizationClick(View v) {
        ApiWrapper.login(inputEmail.getText().toString(),
                inputPassword.getText().toString(), this);
    }

    private boolean isPasswordEquals() {
        return false;
    }

    private boolean isEmailValid() {
        return false;
    }

    @Override
    public void onSuccess(BaseResponseModel<UserModel> response) {
        Log.d(TAG, response.toString());
        account().save(response.getResponse());
        getActivity().finish();
    }

    @Override
    public void onError(ErrorModel errorModel) {

    }
}
