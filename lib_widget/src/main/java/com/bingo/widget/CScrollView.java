package com.bingo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2017/04/28
 *     desc   : 控制自滚动速度,重写滚动回调偏移量;ScrollView嵌套RecyleView的问题
 *     version: 1.0
 * </pre>
 */
public class CScrollView extends ScrollView {

    private Scroller mScroller;
    private OnScrollChangeListener onScrollChangeListener;
    private int downY;
    private int mTouchSlop;

    public CScrollView(Context context) {
        super(context);
        init(context);
    }

    public CScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if( onScrollChangeListener != null ){
            onScrollChangeListener.onScrollChanged(t,oldt);
        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener{
        void onScrollChanged(int t, int oldt);
    }

    /**
     * 调用此方法滚动到目标位置  duration滚动时间
     * 注：继承 NestedScrollView 该方法无效
     */
    public void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();//mScroller.getFinalX();  普通view使用这种方法
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBySlow(int dx, int dy,int duration) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,duration);//scrollView使用的方法（因为可以触摸拖动）
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //普通view使用的方法
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    //    /**
//     * 滑动事件，这是控制手指滑动的惯性速度
//     */
//    @Override
//    public void fling(int velocityY) {
//        super.fling(velocityY / 4);
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
