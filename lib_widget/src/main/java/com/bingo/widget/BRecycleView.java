package com.bingo.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author ：wiggins on 2017/10/17 10:59
 * @e-mail ：traywangjun@gmail.com
 * desc : 解决ScrollView中嵌套recycleView 出现的不显示，显示不全
 * version :1.0
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
