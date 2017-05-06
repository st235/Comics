package sasd97.github.com.comics.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by alexander on 06/05/2017.
 */

public class AutoFitRecyclerView extends RecyclerView {

    private final static int MIN_COLUMN_COUNTER = 1;

    private int columnWidth;
    private GridLayoutManager manager;

    public AutoFitRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoFitRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoFitRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            int[] attrsArray = { android.R.attr.columnWidth };

            TypedArray typedArray = context.obtainStyledAttributes(attrs, attrsArray);
            columnWidth = typedArray.getDimensionPixelSize(0, -1);
            typedArray.recycle();
        }

        manager = new GridLayoutManager(getContext(), MIN_COLUMN_COUNTER);
        setLayoutManager(manager);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (columnWidth > 0) {
            int spanCount = Math.max(MIN_COLUMN_COUNTER, getMeasuredWidth() / columnWidth);
            manager.setSpanCount(spanCount);
        }
    }
}
