package sasd97.github.com.comics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.models.UserModel;
import sasd97.github.com.comics.repositories.AccountRepository;
import sasd97.github.com.comics.ui.base.BaseActivity;
import sasd97.github.com.comics.ui.fragments.HierarchyFragment;
import sasd97.java_blog.xyz.circleview.CircleView;

import static sasd97.github.com.comics.ComicsApp.account;

public class LibraryActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AccountRepository accountRepository = account();

    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @BindString(R.string.navigation_header_sign_in) String signInText;
    @BindString(R.string.navigation_header_comics_user) String comicsUserText;
    @BindString(R.string.navigation_header_comics_copyright) String comicsCopyrightText;

    private View userClickableView;
    private CircleView userAvatarCircleView;
    private TextView userMailTextView;
    private TextView userSignInTextView;
    private ImageView userVerifiedImageView;

    //region base settings
    public LibraryActivity() {
        super(R.layout.activity_library);
    }

    @Override
    protected boolean isButterKnifeEnabled() {
        return true;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected boolean isToolbarEnabled() {
        return true;
    }
    //endregion

    @Override
    protected void onViewCreated(Bundle state) {
        super.onViewCreated(state);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        onNavigationCreate(navigationView);
    }

    private void onNavigationCreate(NavigationView navigationView) {
        View header = navigationView.getHeaderView(0);

        userAvatarCircleView = (CircleView) header.findViewById(R.id.user_avatar_circleview);
        userMailTextView = (TextView) header.findViewById(R.id.user_email);
        userSignInTextView = (TextView) header.findViewById(R.id.user_signin_area);
        userVerifiedImageView = (ImageView) header.findViewById(R.id.user_is_verified);
        userClickableView = header.findViewById(R.id.header_view);

        userClickableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        onSwitchUserHeader();
    }

    private void onSwitchUserHeader() {
        if (accountRepository.isExists()) onUserExists();
        else onUserAbsent();
    }

    private void onUserExists() {
        UserModel user = accountRepository.getUser();

        userAvatarCircleView.setText(user.getEmail());
        userMailTextView.setText(user.getEmail());
        userSignInTextView.setText(comicsCopyrightText);
        userClickableView.setClickable(false);

        if (user.isVerified()) userVerifiedImageView.setVisibility(View.VISIBLE);
    }

    private void onUserAbsent() {
        userAvatarCircleView.setText(comicsUserText);
        userMailTextView.setText(comicsUserText);
        userSignInTextView.setText(signInText);
        userVerifiedImageView.setVisibility(View.GONE);
        userClickableView.setClickable(true);
    }

    private void onUserLogout() {
        accountRepository.remove();
        onSwitchUserHeader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSwitchUserHeader();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_open_file_on_disk:
                pushFragment(HierarchyFragment.newInstance());
                break;
            case R.id.nav_user_logout:
                onUserLogout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
