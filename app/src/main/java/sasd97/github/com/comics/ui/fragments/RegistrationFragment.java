package sasd97.github.com.comics.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.ui.BaseFragment;

/**
 * Created by alexander on 16.03.17.
 */

public class RegistrationFragment extends BaseFragment {

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
        return R.layout.fragment_registration;
    }

    @Override
    protected void onViewCreated(Bundle state) {
        super.onViewCreated(state);


    }

    @OnClick(R.id.registration_button)
    public void onRegistrationClick(View v) {
        if (!isEmailValid(inputEmail.getText())) {
            textLayoutEmail.setError("Its not an email");
            return;
        }

        if (TextUtils.isEmpty(inputPassword.getText())) {
            textLayoutPassword.setError("Field is empty");
            return;
        }
    }

    private boolean isEmailValid(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
