package com.bingo.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
 /**
  * ================================
  * @author:  zcb
  * @email:   zhang-cb@foxmail.com
  * @time:    2019/11/19 15:14
  * @version: 1.0
  * @description: 锚view
  * =================================
  */
public class AnchorView extends FrameLayout {


    public AnchorView(Context context) {
        this(context, null);
    }

    public AnchorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnchorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
