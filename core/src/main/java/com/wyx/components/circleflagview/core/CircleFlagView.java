package com.wyx.components.circleflagview.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: yongxiang.wei
 * @version: 1.2.0, 2019/4/24 17:38
 * @since: 1.2.0
 */
public class CircleFlagView extends View {
    final String DEFAULT_ELLIPSIS = "+";
    final String DEFAULT_TEXT_ELLIPSIS = "";
    final int DEFAULT_TEXT_SIZE = 14;
    final int DEFAULT_TEXT_OVER_WIDTH_SIZE = 12;
    final int DEFAULT_TEXT_COLOR = Color.WHITE;
    final int DEFAULT_BG_COLOR = Color.RED;

    public static final int SHOW_MODE_TEXT = 0;
    public static final int SHOW_MODE_CIRCLE_DOT = 1;

    public static final int TEXT_TYPE_NUMBER = 0;
    public static final int TEXT_TYPE_TEXT = 1;

    final int DEFAULT_MAX_SHOW_NUMBER = 99;

    String mText;
    String mEllipsisText = DEFAULT_ELLIPSIS;
    float mTextSize = DEFAULT_TEXT_SIZE;
    float mTextOverWidthSize = DEFAULT_TEXT_OVER_WIDTH_SIZE;
    int mTextColor = DEFAULT_TEXT_COLOR;
    int mBgColor = DEFAULT_BG_COLOR;


    int mTextType = TEXT_TYPE_NUMBER;
    int mMaxShowNumber = DEFAULT_MAX_SHOW_NUMBER;

    int mShowMode = SHOW_MODE_CIRCLE_DOT;

    Paint mPaint;

    public CircleFlagView(Context context) {
        this(context, null);
    }

    public CircleFlagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleFlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CircleFlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){
        mTextSize = sp2px(context, DEFAULT_TEXT_SIZE);
        mTextOverWidthSize = sp2px(context, DEFAULT_TEXT_OVER_WIDTH_SIZE);
        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleFlagView);

            if(a.hasValue(R.styleable.CircleFlagView_bg_color)){
                mBgColor = a.getColor(R.styleable.CircleFlagView_bg_color, DEFAULT_BG_COLOR);
            }

            if(a.hasValue(R.styleable.CircleFlagView_ellipsis_text)){
                mEllipsisText = a.getString(R.styleable.CircleFlagView_ellipsis_text);
            }

            if(a.hasValue(R.styleable.CircleFlagView_text)){
                mText = a.getString(R.styleable.CircleFlagView_text);
            }

            if(a.hasValue(R.styleable.CircleFlagView_text_size)){
                mTextSize = a.getDimension(R.styleable.CircleFlagView_text_size, sp2px(context, DEFAULT_TEXT_SIZE));
            }

            if(a.hasValue(R.styleable.CircleFlagView_text_over_width_size)){
                mTextOverWidthSize = a.getDimension(R.styleable.CircleFlagView_text_over_width_size, sp2px(context, DEFAULT_TEXT_OVER_WIDTH_SIZE));
            }

            if(a.hasValue(R.styleable.CircleFlagView_text_color)){
                mTextColor = a.getColor(R.styleable.CircleFlagView_text_color, DEFAULT_TEXT_COLOR);
            }

            if(a.hasValue(R.styleable.CircleFlagView_show_mode)){
                mShowMode = a.getInt(R.styleable.CircleFlagView_show_mode, SHOW_MODE_CIRCLE_DOT);
            }

            if(a.hasValue(R.styleable.CircleFlagView_text_type)){
                mTextType = a.getInt(R.styleable.CircleFlagView_text_type, TEXT_TYPE_NUMBER);
            }

            if(a.hasValue(R.styleable.CircleFlagView_max_show_number)){
                mMaxShowNumber = a.getInt(R.styleable.CircleFlagView_max_show_number, DEFAULT_MAX_SHOW_NUMBER);
            }
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        updateIsVisible();
    }

    public CircleFlagView setNumber(int pNumber){
        mText = pNumber < 1 ? "" : pNumber + "";
        return this;
    }

    public int getNumber(){
        if(mTextType == TEXT_TYPE_NUMBER){
            try{
                return Integer.parseInt(mText, 10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    public String getText(){
        return mText;
    }

    public CircleFlagView setText(String pText){
        if(mTextType == TEXT_TYPE_NUMBER){
            try{
                int intVal = Integer.parseInt(pText, 10);
            }catch (Exception e){
                e.printStackTrace();
//                throw(new Exception("setText failure because pText is not number on text_type_nuber mode"));
            }
        }else{
            mText = pText;
        }
        return this;
    }

    public CircleFlagView setEllipsisText(String pEllipsisText){
        mEllipsisText = pEllipsisText;
        return this;
    }

    public CircleFlagView setTextSize(float pTextSize){
        mTextSize = pTextSize;
        return this;
    }

    public CircleFlagView setOverWidthTextSize(float pOverWidthTextSize){
        mTextOverWidthSize = pOverWidthTextSize;
        return this;
    }

    public CircleFlagView setTextColor(int pTextColor){
        mTextColor = pTextColor;
        return this;
    }

    public CircleFlagView setBgColor(int pBgColor){
        mBgColor = pBgColor;
        return this;
    }

    public CircleFlagView setShowMode(int pShowMode){
        if(pShowMode != SHOW_MODE_CIRCLE_DOT && pShowMode != SHOW_MODE_TEXT){
            return this;
        }
        mShowMode = pShowMode;
        return this;
    }

    public CircleFlagView setTextType(int pTextType){
        if(pTextType != TEXT_TYPE_NUMBER && pTextType != TEXT_TYPE_TEXT){
            return this;
        }
        mTextType = pTextType;
        return this;
    }

    public CircleFlagView setMaxShowNumber(int pMaxShowNumber){
        mMaxShowNumber = pMaxShowNumber;
        return this;
    }

    private void updateIsVisible(){
        if(TextUtils.isEmpty(mText)){
            setVisibility(View.GONE);
        }else{
            if(mTextType == TEXT_TYPE_NUMBER){
                try{
                    int intVal = Integer.parseInt(mText, 10);
                    if(intVal < 1){
                        setVisibility(View.GONE);
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            setVisibility(View.VISIBLE);
        }
    }

    public void refreshView(){
        updateIsVisible();
        invalidate();
        return;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        if(mShowMode == SHOW_MODE_TEXT){
            drawText(canvas);
        }else{
            drawCircleDot(canvas);
        }
    }

    private void drawBg(Canvas canvas){
        int width = getWidth();
        int height = getHeight();

        int diameter = width > height ? height : width;

        float cx = width / 2.0f;
        float cy = height / 2.0f;

        float radius = diameter / 2.0f;

        mPaint.setColor(mBgColor);
        canvas.drawCircle(cx, cy, radius, mPaint);

    }

    private void drawText(Canvas canvas){

        int width = getWidth();
        int height = getHeight();

        float cx = width / 2.0f;
        float cy = height / 2.0f;

        StringBuilder sb = new StringBuilder();

        String text = mText;
        boolean resize = false;
        if(mTextType == TEXT_TYPE_TEXT){

        }else{
            try{
                int intVal = Integer.parseInt(text, 10);
                if(intVal > mMaxShowNumber){
                    intVal = mMaxShowNumber;
                    resize = true;
                }
                text = intVal + "";
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        int textLen = TextUtils.isEmpty(text) ? 0 : text.length();
        String tempText;
        float textWidth = 0;
        float textSize = mTextSize;
        mPaint.setTextSize(textSize);
        int len = textLen;

        for(int i = 0; i < len; i++){
            tempText = text.charAt(i) + "";
            textWidth += mPaint.measureText(tempText);
            if(textWidth <= width){
                sb.append(tempText);
            }else {
                resize = true;
                break;
            }
        }
        if(resize){
            textSize = mTextOverWidthSize;
            mPaint.setTextSize(textSize);
            float ellipsisWidth = TextUtils.isEmpty(mEllipsisText) ? 0 : mPaint.measureText(mEllipsisText);
            float enableWidth = width - ellipsisWidth;
            if(enableWidth > 0){
                len = sb.length();
                text = sb.toString();
                sb.delete(0, len);
                textWidth = 0;
                for(int i = 0; i < len; i++){
                    tempText = text.charAt(i) + "";
                    textWidth += mPaint.measureText(tempText);
                    if(textWidth <= enableWidth){
                        sb.append(tempText);
                    }else {
                        break;
                    }
                }
                sb.append(mEllipsisText);
            }else{
                textSize = mTextSize;
            }
        }
        mPaint.setTextSize(textSize);
        mPaint.setColor(mTextColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float distance= (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        cy += distance;
        canvas.drawText(sb.toString(), cx, cy, mPaint);
    }

    private void drawCircleDot(Canvas canvas){
        int width = getWidth();
        int height = getHeight();
    }

    public static float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }
}
