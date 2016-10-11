package com.shiki.gesturelock;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.sse.monitor.R;

import java.util.List;

/**
 * Created by Eric on 2016/10/10.
 */
public class GestureLockPreviewGroup extends RelativeLayout {
    private static final String TAG = "GestureLockPreviewGroup";

    /**
     * 保存所有的GestureLockPreview
     */
    private GestureLockPreview[] mGestureLockPreviews;

    /**
     * 每个边上的GestureLockPreview的个数
     */
    private int mCount = 3;

    /**
     * 每个GestureLockPreview中间的间距 设置为：mGestureLockPreviewWidth
     */
    private int mMarginBetweenView = 30;
    /**
     * GestureLockPreview的边长 mWidth / ( 2 * mCount + 1 )
     */
    private int mGestureLockPreviewWidth;

    /**
     * GestureLockPreview未选中的颜色
     */
    private int mColorNoChecked = 0xFF939090;
    /**
     * GestureLockPreview选中的颜色
     * 0xFF378FC9
     */
    private int mColorChecked = 0xFF378FC9;

    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;

    public GestureLockPreviewGroup(Context context) {
        super(context);
    }

    public GestureLockPreviewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockPreviewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        /**
         * 获得所有自定义的参数的值
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GestureLockPreviewGroup, defStyle, 0);
        int n = a.getIndexCount();
        for (int i =0;i<n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.GestureLockPreviewGroup_color_no_checked:
                    mColorNoChecked = a.getColor(attr,mColorNoChecked);
                    break;
                case R.styleable.GestureLockPreviewGroup_color_checked:
                    mColorChecked = a.getColor(attr,mColorChecked);
                    break;
                case R.styleable.GestureLockPreviewGroup_count_preview:
                    mCount = a.getInt(attr,mCount);
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mHeight = mWidth = mWidth < mHeight ? mWidth : mHeight;

        // 初始化mGestureLockPreviews
        if(mGestureLockPreviews == null){
            mGestureLockPreviews = new GestureLockPreview[mCount * mCount];
            // 计算每个GestureLockPreview的宽度
            mGestureLockPreviewWidth = mWidth / (2*mCount+1);
            // 计算每个GestureLockPreview的间距
            mMarginBetweenView = mGestureLockPreviewWidth;

            for (int i=0;i<mGestureLockPreviews.length;i++){
                //初始化每个GestureLockPreview
                mGestureLockPreviews[i] = new GestureLockPreview(getContext(),mColorNoChecked,mColorChecked);
                mGestureLockPreviews[i].setId(i+1);
                //设置参数，主要是定位GestureLockPreview间的位置
                RelativeLayout.LayoutParams previewParams = new LayoutParams(mGestureLockPreviewWidth,mGestureLockPreviewWidth);
                // 不是每行的第一个，则设置位置为前一个的右边
                if (i % mCount != 0)
                {
                    previewParams.addRule(RelativeLayout.RIGHT_OF,
                            mGestureLockPreviews[i - 1].getId());
                }
                // 从第二行开始，设置为上一行同一位置View的下面
                if (i > mCount - 1)
                {
                    previewParams.addRule(RelativeLayout.BELOW,
                            mGestureLockPreviews[i - mCount].getId());
                }
                //设置右下左上的边距
                int rightMargin = mMarginBetweenView;
                int bottomMargin = mMarginBetweenView;
                int leftMagin = 0;
                int topMargin = 0;
                /**
                 * 每个View都有右外边距和底外边距 第一行的有上外边距 第一列的有左外边距
                 */
                if (i >= 0 && i < mCount)// 第一行
                {
                    topMargin = mMarginBetweenView;
                }
                if (i % mCount == 0)// 第一列
                {
                    leftMagin = mMarginBetweenView;
                }

                previewParams.setMargins(leftMagin, topMargin, rightMargin,
                        bottomMargin);
                mGestureLockPreviews[i].setMode(GestureLockPreview.STATUS_NO_CHECKED);
                addView(mGestureLockPreviews[i], previewParams);
            }

            Log.e(TAG, "mWidth = " + mWidth + " ,  mGestureLockPreviewWidth = "
                    + mGestureLockPreviewWidth + " , mMarginBetweenView = "
                    + mMarginBetweenView);
        }
    }

    /**
     *
     * 做一些必要的重置
     */
    public void reset()
    {
        for (GestureLockPreview gestureLockPreview : mGestureLockPreviews)
        {
            gestureLockPreview.setMode(GestureLockPreview.STATUS_NO_CHECKED);
        }
    }

    public void changeItemMode(List<Integer> chose)
    {
        for (GestureLockPreview gestureLockPreview : mGestureLockPreviews)
        {
            if (chose.contains(gestureLockPreview.getId()))
            {
                gestureLockPreview.setMode(GestureLockPreview.STATUS_CHECKED);
            }
        }
    }
}
