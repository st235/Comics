package sasd97.github.com.comics.ui.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public class ComicViewPager extends ViewPager {

    private float mStartX = 0;
    private OnSwipeOutListener mSwipeOutListener;

    public interface OnSwipeOutListener {
        public void onSwipeOutAtStart();
        public void onSwipeOutAtEnd();
    }

    public ComicViewPager(Context context) {
        super(context);
    }

    public ComicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mSwipeOutListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            float diff = ev.getX() - mStartX;

            if (diff > 0 && getCurrentItem() == 0) {
                if (mSwipeOutListener != null)
                    mSwipeOutListener.onSwipeOutAtStart();
            }
            else if (diff < 0 && getCurrentItem() == (getAdapter().getCount() - 1)) {
                if (mSwipeOutListener != null)
                    mSwipeOutListener.onSwipeOutAtEnd();
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mStartX = ev.getX();
        }

        return super.onInterceptTouchEvent(ev);
    }
}
