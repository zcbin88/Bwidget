package com.bingo.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

 /**
  * ================================
  * @author:  zcb
  * @email:   zhang-cb@foxmail.com
  * @time:    2020-01-15 14:01
  * @version: 1.0
  * @description: 解决ScrollView中嵌套recycleView 出现的不显示，显示不全
  *                 显示不全主要是因为recyclerview不知道数据应该是多高
  * =================================
  */
public class BRecycleView extends RecyclerView {

    public BRecycleView(Context context) {
        super(context);
    }

    public BRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
