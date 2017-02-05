package sasd97.github.com.comics.ui;

import android.content.Intent;

import sasd97.github.com.comics.R;

public class SplashActivity extends BaseActivity {

    public SplashActivity() {
        super();
    }

    @Override
    protected void onBrokenExecution() {
        super.onBrokenExecution();
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
        finish();
    }
}
