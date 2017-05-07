package sasd97.github.com.comics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.http.ApiListener;
import sasd97.github.com.comics.http.ApiWrapper;
import sasd97.github.com.comics.models.BaseResponseModel;
import sasd97.github.com.comics.models.ComicsModel;
import sasd97.github.com.comics.models.ErrorModel;
import sasd97.github.com.comics.ui.base.BaseActivity;

/**
 * Created by alexander on 06/05/2017.
 */

public class ComicsSchemeActivity extends BaseActivity
        implements ApiListener<BaseResponseModel<ComicsModel>> {

    public static final String PARAM_COMICS_ID = "param.comics.id";

    @BindView(R.id.comics_scheme_title) TextView titleTextView;
    @BindView(R.id.comics_scheme_author) TextView authorTextView;
    @BindView(R.id.comics_scheme_cover) AppCompatImageView coverImageView;
    @BindView(R.id.comics_scheme_buy_button) Button buyButton;
    @BindView(R.id.comics_scheme_description) TextView descriptionTextView;

    public ComicsSchemeActivity() {
        super(R.layout.activity_comics_scheme);
    }

    @Override
    protected boolean isButterKnifeEnabled() {
        return true;
    }

    @Override
    protected boolean isToolbarEnabled() {
        return true;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected void onViewCreate() {
        super.onViewCreate();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onViewCreated(Bundle state) {
        super.onViewCreated(state);

        Intent currentIntent = getIntent();

        if (currentIntent == null || !currentIntent.hasExtra(PARAM_COMICS_ID)) {
            onStartError();
            return;
        }

        String comicsId = currentIntent.getStringExtra(PARAM_COMICS_ID);
        ApiWrapper.obtainComics(comicsId, this);
    }

    private void loadComics(ComicsModel comics) {
        Glide
                .with(this)
                .load(comics.getCoverUrl())
                .into(coverImageView);

        titleTextView.setText(comics.getName());
        getToolbar().setTitle(comics.getName());
        descriptionTextView.setText(comics.getDescription());

        buyButton.setText(getString(R.string.comics_scheme_buy, comics.getPrice().obtainPretty()));

        StringBuilder builder = new StringBuilder();
        for (String writer: comics.getWrittenBy()) {
            builder.append(writer).append(" ");
        }

        authorTextView.setText(builder.toString());
    }

    private void onStartError() {
        //todo: show error
    }

    @Override
    public void onSuccess(BaseResponseModel<ComicsModel> response) {
        loadComics(response.getResponse());
    }

    @Override
    public void onError(ErrorModel errorModel) {

    }
}
