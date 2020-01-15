package com.bingo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by lsl on 2016/10/17.
 * 
 */
public class ObserveScrollView extends ScrollView{

    //移动因子, 是一个百分比, 比如手指移动了100px, 那么View就只移动50px
    //目的是达到一个延迟的效果
    private static final float MOVE_FACTOR = 0.5f;

    //松开手指后, 界面回到正常位置需要的动画时间
    private static final int ANIM_TIME = 300;

    //ScrollView的子View， 也是ScrollView的唯一一个子View
    private View contentView;

    //手指按下时的Y值, 用于在移动时计算移动距离
    //如果按下时不能上拉和下拉， 会在手指移动时更新为当前手指的Y值
    private float startY;

    //用于记录正常的布局位置
    private Rect originalRect = new Rect();

    //手指按下时记录是否可以继续下拉
    private boolean canPullDown = false;

    //手指按下时记录是否可以继续上拉
    private boolean canPullUp = false;

    //在手指滑动的过程中记录是否移动了布局
    private boolean isMoved = false;
    private ScrollListener mListener;

    private OnAlphaChanageListener onAlphaChanageListener;

    public interface ScrollListener {//声明接口，用于传递数据

        void scrollOritention(int l, int t, int oldl, int oldt);
    }

    public interface ScrollCallBackDistanse {//声明接口，用于传递数据

        void callback(int i);

        void callbackReduce();
    }

    private ScrollCallBackDistanse scrollCallBackDistanse;

    public ObserveScrollView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObserveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObserveScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.scrollOritention(l, t, oldl, oldt);
        }
//        if (!isMoved) {
        setTitleAlpha(t);
//        }
    }

    public void setScrollListener(ScrollListener l) {
        this.mListener = l;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (contentView == null) return;

        //ScrollView中的唯一子控件的位置信息, 这个位置信息在整个控件的生命周期中保持不变
        originalRect.set(contentView.getLeft(), contentView.getTop(), contentView
                .getRight(), contentView.getBottom());
    }

    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (contentView == null) {
            return super.dispatchTouchEvent(ev);
        }

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                //判断是否可以上拉和下拉
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();

                //记录按下时的Y值
                startY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:

                if (!isMoved) break;  //如果没有移动布局， 则跳过执行

                // 开启动画
                TranslateAnimation anim = new TranslateAnimation(0, 0, contentView.getTop(),
                        originalRect.top);
                anim.setDuration(ANIM_TIME);

                contentView.startAnimation(anim);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if (scrollCallBackDistanse != null) {
                            scrollCallBackDistanse.callbackReduce();
                        }

                        setReduce();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                // 设置回到正常的布局位置
                contentView.layout(originalRect.left, originalRect.top,
                        originalRect.right, originalRect.bottom);

                //将标志位设回false
                canPullDown = false;
                canPullUp = false;
                isMoved = false;

                break;
            case MotionEvent.ACTION_MOVE:

                //在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if (!canPullDown && !canPullUp) {
                    startY = ev.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();

                    break;
                }

                //计算手指移动的距离
                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);

                //是否应该移动布局
                boolean shouldMove =
                        (canPullDown && deltaY > 0)    //可以下拉， 并且手指向下移动
                                || (canPullUp && deltaY < 0)    //可以上拉， 并且手指向上移动
                                || (canPullUp && canPullDown); //既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）

                if (shouldMove) {
                    //计算偏移量
                    int offset = (int) (deltaY * MOVE_FACTOR);

                    //随着手指的移动而移动布局
                    contentView.layout(originalRect.left, originalRect.top + offset,
                            originalRect.right, originalRect.bottom + offset);
                    if (offset > 0) {
                        setScal((float) offset);
                    }
                    if (scrollCallBackDistanse != null) {
                        scrollCallBackDistanse.callback(offset);
                    }

                    isMoved = true;  //记录移动了布局
                }

                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    View Scal_view, title_view;

    /**
     * 需要改变大小的View
     *
     * @param scal_view
     */
    public void setView(View scal_view) {
        this.Scal_view = scal_view;
    }

    /**
     * 需要改变透明度的View
     *
     * @param title_view
     */
    public void setTilte(View title_view) {
        this.title_view = title_view;
    }

    /**
     * 设置标题透明度
     *
     * @param t
     */
    private void setTitleAlpha(int t) {
        if(title_view == null){
            return;
        }
        float alpha = (float) t / (Scal_view.getHeight());
        title_view.setAlpha(alpha);
        if(onAlphaChanageListener != null){
            onAlphaChanageListener.onAlphaChange(alpha);
        }
    }

    /**
     * 设置控件缩放
     *
     * @param i
     */
    private void setScal(float i) {
        if (Scal_view != null) {
            Message message = new Message();
            message.obj = 1.0f + i / (Scal_view.getHeight() / 3);
            handler.sendMessage(message);
        } else {
//            Log.e("提示-------------->", "请传入需要放大缩小的控件");
        }

    }

    /**
     * 恢复大小
     */
    private void setReduce() {
        if (Scal_view != null) {
            float d = Scal_view.getScaleX() - 1;
            for (int i = ANIM_TIME - 1; i >= 0; i--) {
                Message message = new Message();
                message.obj = 1.0f + (d / ANIM_TIME) * i;
                handler.sendMessageDelayed(message, ANIM_TIME - i);
            }
        } else {
//            Log.e("提示-------------->", "请传入需要放大缩小的控件");
        }

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (Scal_view != null) {
                Scal_view.setScaleX((float) msg.obj);
                Scal_view.setScaleY((float) msg.obj);
            } else {
//                Log.e("提示-------------->", "请传入需要放大缩小的控件");
            }

            return false;
        }
    });

    /**
     *
     */
    public void setCallBackDistance(ScrollCallBackDistanse callBackDistance) {
        this.scrollCallBackDistanse = callBackDistance;
    }

    /**
     * 判断是否滚动到顶部
     */
    private boolean isCanPullDown() {
        return getScrollY() == 0 ||
                contentView.getHeight() < getHeight() + getScrollY();
    }

    /**
     * 判断是否滚动到底部
     */
    private boolean isCanPullUp() {
        return contentView.getHeight() <= getHeight() + getScrollY();
    }

    public interface OnAlphaChanageListener{
        void onAlphaChange(float alpha);
    }

    public void setOnAlphaChangeListener(OnAlphaChanageListener listener){
        this.onAlphaChanageListener = listener;
    }
}
