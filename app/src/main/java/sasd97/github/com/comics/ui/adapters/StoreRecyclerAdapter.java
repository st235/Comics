package sasd97.github.com.comics.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.models.ComicsModel;
import sasd97.github.com.comics.ui.base.BaseViewHolder;
import sasd97.github.com.comics.utils.HttpUtils;

/**
 * Created by alexander on 05/05/2017.
 */

public class StoreRecyclerAdapter extends RecyclerView.Adapter<StoreRecyclerAdapter.StoreViewHolder> {

    private static final String TAG = StoreRecyclerAdapter.class.getCanonicalName();

    public interface OnItemClickListener {
        void onClick(int position, ComicsModel comics);
    }

    private Context context;
    private List<ComicsModel> comics;
    private OnItemClickListener onItemClickListener;

    public StoreRecyclerAdapter() {
        this.comics = new ArrayList<>();
    }

    public StoreRecyclerAdapter(@NonNull List<ComicsModel> comics) {
        this.comics = comics;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class StoreViewHolder extends BaseViewHolder {

        @BindView(R.id.comics_preview) ImageView comicsPreviewImageView;
        @BindView(R.id.comics_title) TextView titleTextView;
        @BindView(R.id.comics_price) TextView priceTextView;

        public StoreViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void setupViews() {
        }

        @OnClick(R.id.comics_clickable_layout)
        public void onComicsClick(View v) {
            if (onItemClickListener == null) return;
            onItemClickListener.onClick(getAdapterPosition(), comics.get(getAdapterPosition()));
        }
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_comics_store, parent, false);
        return new StoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        ComicsModel comicsModel = comics.get(position);

        holder.titleTextView.setText(comicsModel.getName());
        holder.priceTextView.setText(comicsModel.getPrice().obtainPretty());

        Glide
                .with(context)
                .load(comicsModel.getCoverUrl())
                .placeholder(R.drawable.ic_launcher)
                .into(holder.comicsPreviewImageView);
    }

    public void add(Collection<ComicsModel> collection) {
        int last = getItemCount();
        this.comics.addAll(collection);
        notifyItemRangeInserted(last, getItemCount());
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }
}
