package sasd97.github.com.comics.ui.fragments;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import sasd97.github.com.comics.R;
import sasd97.github.com.comics.http.ApiListener;
import sasd97.github.com.comics.http.ApiWrapper;
import sasd97.github.com.comics.models.BaseResponseModel;
import sasd97.github.com.comics.models.ComicsModel;
import sasd97.github.com.comics.models.ErrorModel;
import sasd97.github.com.comics.ui.base.BaseFragment;

/**
 * Created by alexander on 05/05/2017.
 */

public class StoreFragment extends BaseFragment
        implements ApiListener<BaseResponseModel<List<ComicsModel>>> {

    private static final String TAG = StoreFragment.class.getCanonicalName();

    @Override
    protected boolean isButterKnifeEnabled() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_store;
    }

    public static StoreFragment newInstance() {
        return new StoreFragment();
    }

    @Override
    protected void onViewCreated(Bundle state) {
        super.onViewCreated(state);

        ApiWrapper.obtainAllComics(0, this);
    }

    @Override
    public void onSuccess(BaseResponseModel<List<ComicsModel>> response) {
        Log.d(TAG, response.toString());
    }

    @Override
    public void onError(ErrorModel errorModel) {

    }
}
