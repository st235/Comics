package sasd97.github.com.comics.ui.fragments;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.ui.BaseFragment;

/**
 * Created by alexander on 16.03.17.
 */

public class AuthorizationFragment extends BaseFragment {

    @BindView(R.id.input_email) EditText inputEmail;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.input_password_confirm) EditText inputPasswordConfirm;
    @BindView(R.id.input_layout_email) TextInputLayout textLayoutEmail;
    @BindView(R.id.input_layout_password) TextInputLayout textLayoutPassword;
    @BindView(R.id.input_layout_password_confirm) TextInputLayout textLayoutPasswordConfirm;

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

    }

    private boolean isPasswordEquals() {
        return false;
    }

    private boolean isEmailValid() {
        return false;
    }
}
