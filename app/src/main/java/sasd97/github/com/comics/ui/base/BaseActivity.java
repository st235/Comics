package sasd97.github.com.comics.ui.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private int layoutId;
    private Toolbar toolbar;

    public BaseActivity() {
        this(0);
    }

    public BaseActivity(@LayoutRes int layoutId) {
        super();
        this.layoutId = layoutId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (layoutId == 0 || dropLifecycleExecution()) {
            onBrokenExecution();
            return;
        }

        setContentView(layoutId);

        if (isButterKnifeEnabled()) ButterKnife.bind(this);

        onViewCreate();
        onViewCreated(savedInstanceState);
    }

    /**
     * Method included in activity <b>lifecycle</b>
     * Calls before views are created
     * Use it when u want to bind views with their representation
     * <b>Not abstract</b> to give ability don`t override it
     * If u are use ButterKnife for example
     */
    protected void onViewCreate() {
            setToolbar();
    }

    /**
     * Method included in activity <b>lifecycle</b>
     * Calls after views are created
     * Use it if u want to set up some views
     * <b>Not abstract</b> to give ability don`t override it
     * If u are use ButterKnife for example
     */
    protected void onViewCreated(Bundle state) {}

    /**
     * Method included in activity <b>lifecycle</b>
     * Calls after the execution was interrupted
     * Use it if u want to close activity after lifecycle interruption
     * <b>Not abstract</b> to give ability don`t override it
     */
    protected void onBrokenExecution() {}

    /**
    * Method included in activity <b>behavior</b>
    * Sugar diamond syntax for <i>findViewById</i>
    * @param resId - the reference to find resource
    * @return view as instance of T generic type
    * @throws ClassCastException
    */
    protected <T> T findView(@IdRes int resId) {
        final View v = findViewById(resId);
        return (T) v;
    }

    /**
     * Method included in activity <b>behavior</b>
     * Signals to activity use ButterKnife bind or not
     * @return boolean flag to signal ButterKnife usage
     */
    protected boolean isButterKnifeEnabled() {
        return false;
    }

    /**
     * Method included in activity <b>behavior</b>
     * Stop execution of methods such: <b>setContentView</b>, <b>onViewCreate</b>
     * @return flag that means is execution are interrupted
     */
    protected boolean dropLifecycleExecution() {
        return false;
    }

    /**
     * Method included in activity <b>behavior</b>
     * Signals about toolbar working mode
     * @return flag that means is toolbar turned off or on
     */
    protected boolean isToolbarEnabled() {
        return false;
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Method included in activity <b>behavior</b>
     * Signals about toolbar view reference
     * @return flag that means is toolbar turned off or on
     */
    protected int getToolbarId() {
            return 0;
    }

    /**
     * Method included in activity <b>behavior</b>
     * Set up a toolbar
     */
    private void setToolbar() {
            if (!isToolbarEnabled()) return;
            toolbar = findView(getToolbarId());
            setSupportActionBar(toolbar);
    }
}