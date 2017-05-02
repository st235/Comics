package sasd97.github.com.comics.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindView;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.ui.adapters.RegistrationAdapter;
import sasd97.github.com.comics.ui.base.BaseActivity;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.fragments_viewpager_adapter) ViewPager fragmentsViewPager;

    @BindArray(R.array.registration_tabs) String[] registrationTabTitles;

    private RegistrationAdapter registrationAdapter;

    public RegistrationActivity() {
        super(R.layout.activity_registration);
    }

    @Override
    protected boolean isButterKnifeEnabled() {
        return true;
    }

    @Override
    protected void onViewCreated(Bundle state) {
        super.onViewCreated(state);

        registrationAdapter = new RegistrationAdapter(getSupportFragmentManager(),
                Arrays.asList(registrationTabTitles));

        fragmentsViewPager.setAdapter(registrationAdapter);

        tabLayout.setupWithViewPager(fragmentsViewPager);
    }
}
