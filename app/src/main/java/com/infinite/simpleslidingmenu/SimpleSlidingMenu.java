package com.infinite.simpleslidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by inf on 2016/8/9.
 */
public class SimpleSlidingMenu extends ViewGroup{

    private static final String TAG="SimpleSlidingMenu";
    private Scroller mScroller;
    private int mScreenWidth,mScreenHeight;
    private int mMarginToEdge;
    private ViewGroup mMenu,mContent;

    private int mMenuWidth;
    private int mContentWidth;
    public SimpleSlidingMenu(Context context) {
        this(context,null);
    }

    public SimpleSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller=new Scroller(context);
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
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentX= (int) event.getX();
                mCurrentY= (int) event.getY();
                int dx=mCurrentX-mLastX;

                //向右滑动，打开菜单
                if (dx>0){
                    //移动的距离达到菜单宽度的时候，不能再滑动
                    if (getScrollX() <=-mMenuWidth){
                        scrollTo(-mMenuWidth,0);
                    }else {
                        scrollBy(-dx,0);
                    }

                }else {//手指向左滑动，关闭菜单
                    //回到初始位置，不再滑动
                    if (getScrollX()>=0){
                        scrollTo(0,0);
                    }else {
                        scrollBy(-dx,0);
                    }
                }
                mMenu.setTranslationX((getScrollX()+mMenuWidth)*2/3);
                mLastX=mCurrentX;
                mLastY=mCurrentY;
                break;
            case MotionEvent.ACTION_UP:
                //打开菜单
                if (Math.abs((float) getScrollX()/mMenuWidth)>=0.5){
                    mScroller.startScroll(getScrollX(),0,-mMenuWidth-getScrollX(),0);
                }else{//关闭
                    mScroller.startScroll(getScrollX(),0,-getScrollX(),0);
                }
                invalidate();
                break;
        }
        return true;
    }

    int x= 0;
    int y= 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept=false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept=false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx= (int) (ev.getX()-x);
                int dy= (int) (ev.getY()-y);
                if (Math.abs(dx)>Math.abs(dy)){
                    intercept=true;
                }else{
                    intercept=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept=false;
                break;
        }
        mLastX= (int) ev.getX();
        mLastY= (int) ev.getY();
        x= (int) ev.getX();
        y= (int) ev.getY();
        return intercept;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            mMenu.setTranslationX((getScrollX()+mMenuWidth)*2/3);
            invalidate();
        }
    }
}
