package com.bingo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
  * ================================
  * @author:  zcb
  * @email:   zcbin2@grgbanking.com
  * @time:    2019/4/19 11:02
  * @version: 1.0
  * @description: 带过渡动画的折叠收缩布局
  * =================================
  */
public class ExpandLayout extends LinearLayout {

    public ExpandLayout(Context context) {
        this(context, null);
        initView();
    }

    public ExpandLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();
    }

    public ExpandLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private View layoutView;
    private int viewHeight;
    private boolean isExpand;
    private long animationDuration;
    private boolean lock;

    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
        layoutView = this;
        isExpand = true;
        animationDuration = 300;
        setViewDimensions();
    }

    /**
     * @param isExpand 初始状态是否折叠
     */
    public void initExpand(boolean isExpand) {
        this.isExpand = isExpand;
        setViewDimensions();
    }

    /**
     * 设置动画时间
     *
     * @param animationDuration 动画时间
     */
    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * 获取subView的总高度
     * View.post()的runnable对象中的方法会在View的measure、layout等事件后触发
     */
    private void setViewDimensions() {
        layoutView.post(new Runnable() {
            @Override
            public void run() {
                if (viewHeight <= 0) {
                    int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
                    viewHeight = expandSpec;
//                    viewHeight = layoutView.getMeasuredHeight();
                }
                setViewHeight(layoutView, isExpand ? viewHeight : 0);
            }
        });
    }

//    @Override
//    protected void onMeasure(int widthSpec, int heightSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthSpec, expandSpec);
//    }

    public static void setViewHeight(View view, int height) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.requestLayout();
    }

    /**
     * 切换动画实现
     */
    private void animateToggle(long animationDuration) {
        ValueAnimator heightAnimation = isExpand ?
                ValueAnimator.ofFloat(0f, viewHeight) : ValueAnimator.ofFloat(viewHeight, 0f);
        heightAnimation.setDuration(animationDuration / 2);
        heightAnimation.setStartDelay(animationDuration / 2);

        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) (float) animation.getAnimatedValue();
                setViewHeight(layoutView, value);
                if (value == viewHeight || value == 0) {
                    lock = false;
                }
            }
        });

        heightAnimation.start();
        lock = true;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 折叠view
     */
    public void collapse() {
        isExpand = false;
        animateToggle(animationDuration);
    }
    public void collapse(TextView tv) {
        isExpand = false;
        animateToggle(animationDuration);
        // 使用代码设置drawableleft
        Drawable right = getResources().getDrawable(R.drawable.icon_right);
        right.setBounds(0, 0, right.getMinimumWidth(),right.getMinimumHeight());

        Drawable down = getResources().getDrawable(R.drawable.icon_down);
        down.setBounds(0, 0, down.getMinimumWidth(),down.getMinimumHeight());
        tv.setCompoundDrawables(null, null, right, null);
    }

    /**
     * 展开view
     */
    public void expand() {
        isExpand = true;
        animateToggle(animationDuration);
    }
    public void expand(TextView tv) {
        isExpand = true;
        animateToggle(animationDuration);
        // 使用代码设置drawableleft
        Drawable right = getResources().getDrawable(R.drawable.icon_right);
        right.setBounds(0, 0, right.getMinimumWidth(),right.getMinimumHeight());

        Drawable down = getResources().getDrawable(R.drawable.icon_down);
        down.setBounds(0, 0, down.getMinimumWidth(),down.getMinimumHeight());
        tv.setCompoundDrawables(null, null, down, null);
    }

    public void toggleExpand() {
        if (lock) {
            return;
        }
        if (isExpand) {
            collapse();
        } else {
            expand();
        }
    }
    public void toggleExpand(TextView tv) {
        if (lock) {
            return;
        }

        // 使用代码设置drawableleft
        Drawable right = getResources().getDrawable(R.drawable.icon_right);
        right.setBounds(0, 0, right.getMinimumWidth(),right.getMinimumHeight());

        Drawable down = getResources().getDrawable(R.drawable.icon_down);
        down.setBounds(0, 0, down.getMinimumWidth(),down.getMinimumHeight());
        if (isExpand) {
            collapse();
            tv.setCompoundDrawables(null, null, right, null);
        } else {
            expand();
            tv.setCompoundDrawables(null, null, down, null);
        }
    }
}
