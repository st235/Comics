package sasd97.github.com.comics.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.components.PageImageView;
import sasd97.github.com.comics.constants.PageImageConstants;
import sasd97.github.com.comics.parsers.IParser;
import sasd97.github.com.comics.parsers.ParserFactory;
import sasd97.github.com.comics.parsers.RarParser;
import sasd97.github.com.comics.services.LocalComicHandler;
import sasd97.github.com.comics.ui.adapters.ComicViewPager;
import sasd97.github.com.comics.utils.AndroidVersionUtils;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public class ReaderFragment extends Fragment implements View.OnTouchListener {

    private static final String CACHE_DIR = "comic_cached";
    private final static HashMap<Integer, PageImageConstants.PageViewMode> RESOURCE_VIEW_MODE;

    public static final int RESULT = 1;
    public static final String RESULT_CURRENT_PAGE = "fragment.reader.currentpage";

    public static final String PARAM_HANDLER = "PARAM_HANDLER";
    public static final String PARAM_MODE = "PARAM_MODE";

    public static final String STATE_FULLSCREEN = "STATE_FULLSCREEN";
    public static final String STATE_NEW_COMIC = "STATE_NEW_COMIC";
    public static final String STATE_NEW_COMIC_TITLE = "STATE_NEW_COMIC_TITLE";

    @BindView(R.id.viewPager) ComicViewPager comicViewPager;

    private LinearLayout pageNavigationLayout;
    private SeekBar pageNavigationSeekBar;
    private TextView pageNavigationTextView;

    private ComicPagerAdapter mPagerAdapter;
    private GestureDetector mGestureDetector;

    private boolean mIsFullscreen;
    private int mCurrentPage;
    private String mFilename;
    private PageImageConstants.PageViewMode mPageViewMode;
    private boolean mIsLeftToRight;
    private float mStartingX;

    private IParser parser;
    private Picasso picasso;
    private LocalComicHandler comicHandler;
    private SparseArray<Target> targetsHolder = new SparseArray<>();

    private int mNewComicTitle;

    public enum Mode {
        MODE_LIBRARY,
        MODE_BROWSER;
    }

    static {
        RESOURCE_VIEW_MODE = new HashMap<>();
        RESOURCE_VIEW_MODE.put(R.id.view_mode_aspect_fill, PageImageConstants.PageViewMode.ASPECT_FILL);
        RESOURCE_VIEW_MODE.put(R.id.view_mode_aspect_fit, PageImageConstants.PageViewMode.ASPECT_FIT);
        RESOURCE_VIEW_MODE.put(R.id.view_mode_fit_width, PageImageConstants.PageViewMode.FIT_WIDTH);
    }


    public static ReaderFragment create(File comic) {
        ReaderFragment fragment = new ReaderFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_MODE, Mode.MODE_BROWSER);
        args.putSerializable(PARAM_HANDLER, comic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        File file = (File) bundle.getSerializable(PARAM_HANDLER);

        parser = ParserFactory.create(file);
        mFilename = file.getName();

        mCurrentPage = Math.max(1, Math.min(mCurrentPage, parser.pageAmount()));

        comicHandler = new LocalComicHandler(parser);
        picasso = new Picasso.Builder(getActivity())
                .addRequestHandler(comicHandler)
                .build();
        mPagerAdapter = new ComicPagerAdapter();
        mPageViewMode = PageImageConstants.PageViewMode.values()[0];
        mGestureDetector = new GestureDetector(getActivity(), new MyTouchListener());


        if (parser instanceof RarParser) {
            File cacheDir = new File(getActivity().getExternalCacheDir(), CACHE_DIR);
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            } else {
                for (File f : cacheDir.listFiles()) {
                    f.delete();
                }
            }
            ((RarParser) parser).setCache(cacheDir);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_reader, container, false);
        ButterKnife.bind(this, v);

        pageNavigationLayout = (LinearLayout) getActivity().findViewById(R.id.pageNavLayout);
        pageNavigationSeekBar = (SeekBar) pageNavigationLayout.findViewById(R.id.pageSeekBar);
        pageNavigationTextView = (TextView) pageNavigationLayout.findViewById(R.id.pageNavTextView);

        comicViewPager.setAdapter(mPagerAdapter);
        comicViewPager.setOffscreenPageLimit(3);
        comicViewPager.setOnTouchListener(this);

        comicViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (mIsLeftToRight) setCurrentPage(position + 1);
                else setCurrentPage(comicViewPager.getAdapter().getCount() - position);
            }
        });

        pageNavigationSeekBar.setMax(parser.pageAmount() - 1);
        pageNavigationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (mIsLeftToRight)
                        setCurrentPage(progress + 1);
                    else
                        setCurrentPage(pageNavigationSeekBar.getMax() - progress + 1);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                picasso.pauseTag(ReaderFragment.this.getActivity());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                picasso.resumeTag(ReaderFragment.this.getActivity());
            }
        });

        if (mCurrentPage != -1) {
            setCurrentPage(mCurrentPage);
            mCurrentPage = -1;
        }

        if (savedInstanceState != null) {
            boolean fullscreen = savedInstanceState.getBoolean(STATE_FULLSCREEN);
            setFullscreen(fullscreen);

            int newComicId = savedInstanceState.getInt(STATE_NEW_COMIC);
            if (newComicId != -1) {
                int titleRes = savedInstanceState.getInt(STATE_NEW_COMIC_TITLE);
                //confirmSwitch(Storage.getStorage(getActivity()).getComic(newComicId), titleRes);
            }
        } else {
            setFullscreen(true);
        }

        getActivity().setTitle(mFilename);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reader, menu);

        switch (mPageViewMode) {
            case ASPECT_FILL:
                menu.findItem(R.id.view_mode_aspect_fill).setChecked(true);
                break;
            case ASPECT_FIT:
                menu.findItem(R.id.view_mode_aspect_fit).setChecked(true);
                break;
            case FIT_WIDTH:
                menu.findItem(R.id.view_mode_fit_width).setChecked(true);
                break;
        }

        if (mIsLeftToRight) {
            menu.findItem(R.id.reading_left_to_right).setChecked(true);
        }
        else {
            menu.findItem(R.id.reading_right_to_left).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //SharedPreferences.Editor editor = mPreferences.edit();
        switch(item.getItemId()) {
            case R.id.view_mode_aspect_fill:
            case R.id.view_mode_aspect_fit:
            case R.id.view_mode_fit_width:
                item.setChecked(true);
                mPageViewMode = RESOURCE_VIEW_MODE.get(item.getItemId());
//                editor.putInt(Constants.SETTINGS_PAGE_VIEW_MODE, mPageViewMode.native_int);
//                editor.apply();
                updatePageViews(comicViewPager);
                break;
            case R.id.reading_left_to_right:
            case R.id.reading_right_to_left:
                item.setChecked(true);
                int page = getCurrentPage();
                mIsLeftToRight = (item.getItemId() == R.id.reading_left_to_right);
//                editor.putBoolean(Constants.SETTINGS_READING_LEFT_TO_RIGHT, mIsLeftToRight);
//                editor.apply();
                setCurrentPage(page, false);
                comicViewPager.getAdapter().notifyDataSetChanged();
                updateSeekBar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        try {
            parser.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        picasso.shutdown();
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public int getCurrentPage() {
        if (mIsLeftToRight)
            return comicViewPager.getCurrentItem() + 1;
        else
            return comicViewPager.getAdapter().getCount() - comicViewPager.getCurrentItem();
    }

    private void setCurrentPage(int page) {
        setCurrentPage(page, true);
    }

    private void setCurrentPage(int page, boolean animated) {
        if (mIsLeftToRight) {
            comicViewPager.setCurrentItem(page - 1);
            pageNavigationSeekBar.setProgress(page - 1);
        } else {
            comicViewPager.setCurrentItem(comicViewPager.getAdapter().getCount() - page, animated);
            pageNavigationSeekBar.setProgress(comicViewPager.getAdapter().getCount() - page);
        }

        String navPage = new StringBuilder()
                .append(page).append("/").append(parser.pageAmount())
                .toString();

        pageNavigationTextView.setText(navPage);
    }

    private class ComicPagerAdapter extends PagerAdapter {

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return parser.pageAmount();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.fragment_reader_page, container, false);

            PageImageView pageImageView = (PageImageView) layout.findViewById(R.id.pageImageView);
            if (mPageViewMode == PageImageConstants.PageViewMode.ASPECT_FILL)
                pageImageView.setTranslateToRightEdge(!mIsLeftToRight);
            pageImageView.setViewMode(PageImageConstants.PageViewMode.ASPECT_FILL);
            pageImageView.setOnTouchListener(ReaderFragment.this);

            container.addView(layout);

            MyTarget t = new MyTarget(layout, position);
            loadImage(t);
            targetsHolder.put(position, t);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View layout = (View) object;
            picasso.cancelRequest(targetsHolder.get(position));
            targetsHolder.delete(position);
            container.removeView(layout);
            ImageView iv = (ImageView) layout.findViewById(R.id.pageImageView);
            Drawable drawable = iv.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bd = (BitmapDrawable) drawable;
                Bitmap bm = bd.getBitmap();
                if (bm != null) {
                    bm.recycle();
                }
            }
        }
    }

    private void loadImage(MyTarget t) {
        int pos;
        if (mIsLeftToRight) {
            pos = t.position;
        } else {
            pos = comicViewPager.getAdapter().getCount() - t.position - 1;
        }

        picasso.load(comicHandler.getPageUri(pos))
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .tag(getActivity())
                .resize(1600, 2000)
                .centerInside()
                .onlyScaleDown()
                .into(t);
    }

    private class MyTarget implements Target, View.OnClickListener {
        private WeakReference<View> mLayout;
        public final int position;

        public MyTarget(View layout, int position) {
            mLayout = new WeakReference<>(layout);
            this.position = position;
        }

        private void setVisibility(int imageView, int progressBar, int reloadButton) {
            View layout = mLayout.get();
            layout.findViewById(R.id.pageImageView).setVisibility(imageView);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            View layout = mLayout.get();
            if (layout == null) return;

            setVisibility(View.VISIBLE, View.GONE, View.GONE);
            ImageView iv = (ImageView) layout.findViewById(R.id.pageImageView);
            iv.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            View layout = mLayout.get();
            if (layout == null) return;
            Log.d("FAILED", "FAILED");
            setVisibility(View.GONE, View.GONE, View.VISIBLE);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {}

        @Override
        public void onClick(View v) {
            View layout = mLayout.get();
            if (layout == null)
                return;

            setVisibility(View.GONE, View.VISIBLE, View.GONE);
            loadImage(this);
        }
    }

    private class MyTouchListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (!isFullscreen()) {
                setFullscreen(true, true);
                return true;
            }

            float x = e.getX();

            // tap left edge
            if (x < (float) comicViewPager.getWidth() / 3) {
                if (mIsLeftToRight) {
                    if (getCurrentPage() == 1);
                        //hitBeginning();
                    else
                        setCurrentPage(getCurrentPage() - 1);
                } else {
                    if (getCurrentPage() == comicViewPager.getAdapter().getCount());
                        //hitEnding();
                    else
                        setCurrentPage(getCurrentPage() + 1);
                }
            }
            // tap right edge
            else if (x > (float) comicViewPager.getWidth() / 3 * 2) {
                if (mIsLeftToRight) {
                    if (getCurrentPage() == comicViewPager.getAdapter().getCount());
                        //hitEnding();;
                    else
                        setCurrentPage(getCurrentPage() + 1);
                } else {
                    if (getCurrentPage() == 1);
                       // hitBeginning();
                    else
                        setCurrentPage(getCurrentPage() - 1);
                }
            } else
                setFullscreen(false, true);

            return true;
        }
    }

    private void updatePageViews(ViewGroup parentView) {
        for (int i = 0; i < parentView.getChildCount(); i++) {
            final View child = parentView.getChildAt(i);
            if (child instanceof ViewGroup) {
                updatePageViews((ViewGroup) child);
            } else if (child instanceof PageImageView) {
                PageImageView view = (PageImageView) child;
                if (mPageViewMode == PageImageConstants.PageViewMode.ASPECT_FILL)
                    view.setTranslateToRightEdge(!mIsLeftToRight);
                view.setViewMode(mPageViewMode);
            }
        }
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    private void setFullscreen(boolean fullscreen) {
        setFullscreen(fullscreen, false);
    }

    private void setFullscreen(boolean fullscreen, boolean animated) {
        mIsFullscreen = fullscreen;

        ActionBar actionBar = getActionBar();

        if (fullscreen) {
            if (actionBar != null) actionBar.hide();

            int flag =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (AndroidVersionUtils.isKitKatOrLater()) {
                flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                flag |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                flag |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            comicViewPager.setSystemUiVisibility(flag);

            pageNavigationLayout.setVisibility(View.INVISIBLE);
        } else {
            if (actionBar != null) actionBar.show();

            int flag =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (AndroidVersionUtils.isKitKatOrLater()) {
                flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }
            comicViewPager.setSystemUiVisibility(flag);

            pageNavigationLayout.setVisibility(View.VISIBLE);

            // status bar & navigation bar background won't show in some cases
            if (AndroidVersionUtils.isLollipopOrLater()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Window w = getActivity().getWindow();
                        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    }
                }, 300);
            }
        }
    }

    private boolean isFullscreen() {
        return mIsFullscreen;
    }

    private void updateSeekBar() {
        int seekRes = (mIsLeftToRight)
                ? R.drawable.reader_progress
                : R.drawable.reader_progress_inverse;

        Drawable d = ContextCompat.getDrawable(getActivity(), seekRes);
        Rect bounds = pageNavigationSeekBar.getProgressDrawable().getBounds();
        pageNavigationSeekBar.setProgressDrawable(d);
        pageNavigationSeekBar.getProgressDrawable().setBounds(bounds);
    }
}