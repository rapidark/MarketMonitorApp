package com.shiki.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Eric on 2016/10/10.
 */
public class GestureLockPreview extends View{
    private static final String TAG = "GestureLockPreview";

    /**
     * GestureLockPreview的两种状态
     */
    public static final int STATUS_NO_CHECKED = 0;
    public static final int STATUS_CHECKED = 1;

    /**
     * GestureLockView的当前状态
     */
    private int mCurrentStatus = STATUS_NO_CHECKED;

    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;

    /**
     * 两个颜色，可由用户自定义，初始化时由GestureLockPreviewGroup传入
     */
    private int mColorNoChecked;
    private int mColorChecked;

    /**
     * 画笔的宽度
     */
    private int mStrokeWidth = 2;

    private Paint mPaint;

    public GestureLockPreview(Context context, int colorNoChecked, int colorChecked) {
        super(context);
        this.mColorNoChecked = colorNoChecked;
        this.mColorChecked = colorChecked;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 取长和宽中的小值
        mWidth = mWidth < mHeight ? mWidth : mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentStatus){
            case STATUS_NO_CHECKED:
                mPaint.setColor(mColorNoChecked);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawCircle(mWidth/2,mWidth/2,mWidth,mPaint);
                //canvas.drawRect(0,0,mWidth,mWidth,mPaint);
                break;
            case STATUS_CHECKED:
                mPaint.setColor(mColorChecked);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawCircle(mWidth/2,mWidth/2,mWidth,mPaint);
                //canvas.drawRect(0,0,mWidth,mWidth,mPaint);
                break;
        }
    }

    /**
     * 设置当前模式并重绘界面
     *
     * @param mode
     */
    public void setMode(int mode)
    {
        this.mCurrentStatus = mode;
        invalidate();
    }
}
