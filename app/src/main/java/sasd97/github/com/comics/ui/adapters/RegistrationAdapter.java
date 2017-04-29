package sasd97.github.com.comics.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

import sasd97.github.com.comics.ui.RegistrationActivity;
import sasd97.github.com.comics.ui.fragments.AuthorizationFragment;
import sasd97.github.com.comics.ui.fragments.RegistrationFragment;

/**
 * Created by alexander on 16.03.17.
 */

public class RegistrationAdapter extends FragmentPagerAdapter {

    private static final int PAGER_COUNT = 2;

    private List<String> titles;

    public RegistrationAdapter(FragmentManager fragmentManager, List<String> titles) {
        super(fragmentManager);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RegistrationFragment();
            case 1:
                return new AuthorizationFragment();
            default:
                return new RegistrationFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
