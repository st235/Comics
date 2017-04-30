package sasd97.github.com.comics.ui;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;

import sasd97.github.com.comics.ui.base.BaseActivity;
import sasd97.github.com.comics.utils.AndroidVersionUtils;
import sasd97.github.com.comics.utils.PermissionUtils;

public class SplashActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_ID = 1;

    public SplashActivity() {
        super();
    }

    @Override
    protected void onBrokenExecution() {
        super.onBrokenExecution();

        if (AndroidVersionUtils.isMarshmallowOrLater()) {
            PermissionUtils.request(PERMISSION_REQUEST_ID, this,
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }

        closeActivity();
    }

    private void closeActivity() {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        closeActivity();
    }
}
