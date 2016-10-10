package com.shiki.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Eric on 2016/9/29.
 */
public class GestureLockView extends View {
    private static final String TAG = "GestureLockView";

    /**
     * GestureLockView的三种状态
     */
    public static final int STATUS_NO_FINGER = 0;
    public static final int STATUS_FINGER_ON = 1;
    public static final int STATUS_FINGER_UP = 2;
    /**
     * GestureLockView的当前状态
     */
    private int mCurrentStatus = STATUS_NO_FINGER;

    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;
    /**
     * 外圆半径
     */
    private int mRadius;
    /**
     * 画笔的宽度
     */
    private int mStrokeWidth = 4;

    /**
     * 圆心坐标
     */
    private int mCenterX;
    private int mCenterY;

    /**
     * 内圆的半径 = mInnerCircleRadiusRate * mRadus
     *
     */
    private float mInnerCircleRadiusRate = 0.33F;

    /**
     * 四个颜色，可由用户自定义，初始化时由GestureLockViewGroup传入
     */
    private int mColorNoFingerInner;
    private int mColorNoFingerOutter;
    private int mColorFingerOn;
    private int mColorFingerUp;

    private Paint mPaint;

    public GestureLockView(Context context, int colorNoFingerInner, int colorNoFingerOutter, int colorFingerOn, int colorFingerUp) {
        super(context);
        this.mColorNoFingerInner = colorNoFingerInner;
        this.mColorNoFingerOutter = colorNoFingerOutter;
        this.mColorFingerOn = colorFingerOn;
        this.mColorFingerUp = colorFingerUp;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 取长和宽中的小值
        mWidth = mWidth < mHeight ? mWidth : mHeight;
        mRadius = mCenterX = mCenterY = mWidth / 2;
        mRadius -= mStrokeWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mCurrentStatus){
            case STATUS_NO_FINGER:
                // 绘制外圆
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mColorNoFingerOutter);
                mPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);
                break;
            case STATUS_FINGER_ON:
                // 绘制外圆
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mColorFingerOn);
                mPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);
                // 绘制内圆
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCenterX,mCenterY,mRadius*mInnerCircleRadiusRate,mPaint);
                break;
            case STATUS_FINGER_UP:
                // 绘制外圆
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mColorFingerUp);
                mPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);
                // 绘制内圆
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCenterX,mCenterY,mRadius*mInnerCircleRadiusRate,mPaint);
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
