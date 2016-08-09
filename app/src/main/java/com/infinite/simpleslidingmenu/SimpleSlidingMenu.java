package com.infinite.simpleslidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by inf on 2016/8/9.
 */
public class SimpleSlidingMenu extends ViewGroup{

    private static final String TAG="SimpleSlidingMenu";
    private int mScreenWidth,mScreenHeight;
    private int mMarginToEdge;
    private ViewGroup mMenu,mContent;

    private int mMenuWidth;
    private int mContentWidth;
    private int mScrolledDistance;
    public SimpleSlidingMenu(Context context) {
        this(context,null);
    }

    public SimpleSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight=dm.heightPixels;
        mScreenWidth=dm.widthPixels;
        mMarginToEdge=convertToDp(getContext(),100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMenu= (ViewGroup) getChildAt(0);
        mContent= (ViewGroup) getChildAt(1);

        mMenuWidth=mScreenWidth-mMarginToEdge;

        mContentWidth=mScreenWidth;

        measureChild(mMenu,widthMeasureSpec,heightMeasureSpec);
        measureChild(mContent,widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(mMenuWidth+mContentWidth,mScreenHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mMenu.layout(-mMenuWidth,0,0,mScreenHeight);
        mContent.layout(0,0,mScreenWidth,mScreenHeight);
    }

    private int convertToDp(Context context , int num){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, context.getResources().getDisplayMetrics());
    }

    private int mLastX,mLastY,mCurrentX,mCurrentY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX= (int) event.getX();
                mLastY= (int) event.getY();
                Log.e(TAG,"action down:"+mLastX);
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentX= (int) event.getX();
                mCurrentY= (int) event.getY();
                Log.e(TAG,"action move:"+mCurrentX);
                mScrolledDistance=mCurrentX-mLastX;
                scrollBy(-mScrolledDistance,0);
                mLastX=mCurrentX;
                mLastY=mCurrentY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
