package com.bingo.widget.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ================================
 *
 * @author: zcb
 * @email: zcbin2@grgbanking.com
 * @time: 2018/8/29 14:28
 * @version: 1.0
 * @description: =================================
 */
public class CommonGridView extends GridView {
    public CommonGridView(Context context) {
        super(context);
    }

    public CommonGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //重写方法，防止ScrollView中使用Gridview只显示一行
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
