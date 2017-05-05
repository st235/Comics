package sasd97.github.com.comics.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by alexander on 19.02.17.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        setupViews();
    }

    protected abstract void setupViews();
}
