package sasd97.github.com.comics.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import sasd97.github.com.comics.R;
import sasd97.github.com.comics.ui.base.BaseActivity;
import sasd97.github.com.comics.ui.fragments.AuthorizationFragment;
import sasd97.github.com.comics.ui.fragments.RegistrationFragment;

public class AuthorizationActivity extends BaseActivity
        implements AuthorizationFragment.OnAccountStateListener {

    public AuthorizationActivity() {
        super(R.layout.activity_registration);
    }

    @Override
    protected boolean isButterKnifeEnabled() {
        return true;
    }

    @Override
    protected void onViewCreated(Bundle state) {
        super.onViewCreated(state);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new AuthorizationFragment())
                .commit();
    }

    private void pushFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private boolean popFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    @Override
    public void onRegisterSwitch() {
        pushFragment(new RegistrationFragment());
    }
}
