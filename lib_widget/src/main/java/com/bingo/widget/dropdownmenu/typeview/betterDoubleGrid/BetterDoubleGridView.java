package com.bingo.widget.dropdownmenu.typeview.betterDoubleGrid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bingo.widget.R;
import com.bingo.widget.dropdownmenu.entity.FilterUrl;
import com.bingo.widget.dropdownmenu.interfaces.OnFilterDoneListener;

import java.util.List;


/**
 * auther: baiiu
 * time: 16/6/5 05 23:03
 * description:
 */
public class BetterDoubleGridView extends LinearLayout implements View.OnClickListener {

    View rootView;
    RecyclerView recyclerView;
    Button btnConfirm;

    private List<String> mTopGridData;
    private List<String> mBottomGridList;
    private OnFilterDoneListener mOnFilterDoneListener;


    public BetterDoubleGridView(Context context) {
        this(context, null);
    }

    public BetterDoubleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BetterDoubleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BetterDoubleGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);
        rootView=inflate(context, R.layout.merge_filter_double_grid, this);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        btnConfirm = rootView.findViewById(R.id.bt_confirm);
        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDone();
            }
        });
    }


    public BetterDoubleGridView setmTopGridData(List<String> mTopGridData) {
        this.mTopGridData = mTopGridData;
        return this;
    }

    public BetterDoubleGridView setmBottomGridList(List<String> mBottomGridList) {
        this.mBottomGridList = mBottomGridList;
        return this;
    }

    public BetterDoubleGridView build() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                if (position == 0 || position == mTopGridData.size() + 1) {
                    return 4;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new DoubleGridAdapter(getContext(), mTopGridData, mBottomGridList, this));

        return this;
    }

    private TextView mTopSelectedTextView;
    private TextView mBottomSelectedTextView;

    @Override public void onClick(View v) {

        TextView textView = (TextView) v;
        String text = (String) textView.getTag();

        if (textView == mTopSelectedTextView) {
            mTopSelectedTextView = null;
            textView.setSelected(false);
        } else if (textView == mBottomSelectedTextView) {
            mBottomSelectedTextView = null;
            textView.setSelected(false);
        } else if (mTopGridData.contains(text)) {
            if (mTopSelectedTextView != null) {
                mTopSelectedTextView.setSelected(false);
            }
            mTopSelectedTextView = textView;
            textView.setSelected(true);
        } else {
            if (mBottomSelectedTextView != null) {
                mBottomSelectedTextView.setSelected(false);
            }
            mBottomSelectedTextView = textView;
            textView.setSelected(true);
        }
    }


    public BetterDoubleGridView setOnFilterDoneListener(OnFilterDoneListener listener) {
        mOnFilterDoneListener = listener;
        return this;
    }

    public void clickDone() {
        FilterUrl.instance().doubleGridTop = mTopSelectedTextView == null ? "" : (String) mTopSelectedTextView.getTag();
        FilterUrl.instance().doubleGridBottom =
                mBottomSelectedTextView == null ? "" : (String) mBottomSelectedTextView.getTag();

        if (mOnFilterDoneListener != null) {
            mOnFilterDoneListener.onFilterDone(3, "", "");
        }
    }

}
