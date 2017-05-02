package sasd97.java_blog.xyz.circleview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import sasd97.java_blog.xyz.circleview.utils.Abbreviation;
import sasd97.java_blog.xyz.circleview.utils.Dimens;

/**
 * Created by alexander on 30/04/2017.
 */

public class CircleView extends View {

    private static final String TAG = CircleView.class.getCanonicalName();

    private int textSize;
    private int backgroundColor;
    private int textForegroundColor;

    private int centerX;
    private int centerY;
    private int backgroundRadius;

    private String text;

    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVars(context, attrs);
        initPaints();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVars(context, attrs);
        initPaints();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initVars(context, attrs);
        initPaints();
    }

    private void initVars(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

        text = Abbreviation.make(typedArray.getString(R.styleable.CircleView_cv_text));
        textSize = typedArray.getDimensionPixelSize(R.styleable.CircleView_cv_textSize, 0);
        backgroundColor = typedArray.getColor(R.styleable.CircleView_cv_backgroundColor, Color.CYAN);
        textForegroundColor = typedArray.getColor(R.styleable.CircleView_cv_textColor, Color.BLACK);

        typedArray.recycle();
    }

    private void initPaints() {
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(backgroundColor);

        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textForegroundColor);
    }

    public void setText(String text) {
        this.text = Abbreviation.make(text);
    }

    public void setTextSize(int textSize) {
        this.textSize = Dimens.dpToPx(textSize);
        textPaint.setTextSize(this.textSize);
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        backgroundPaint.setColor(backgroundColor);
    }

    public void setTextForegroundColor(int textForegroundColor) {
        this.textForegroundColor = textForegroundColor;
        textPaint.setColor(textForegroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(centerX, centerY, backgroundRadius, backgroundPaint);
        canvas.drawText(text, centerX, centerY - (textPaint.descent() + textPaint.ascent()) / 2, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        backgroundRadius = Math.min(getWidth(), getHeight()) / 2;
    }
}
