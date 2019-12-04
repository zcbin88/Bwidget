package com.bingo.widget.dropdownmenu.typeview.betterDoubleGrid.holder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bingo.widget.R;
import com.bingo.widget.dropdownmenu.util.UIUtil;
import com.bingo.widget.dropdownmenu.view.FilterCheckedTextView;


/**
 * auther: baiiu
 * time: 16/6/5 05 23:35
 * description:
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final FilterCheckedTextView textView;
    private View.OnClickListener mListener;

    public ItemViewHolder(Context mContext, ViewGroup parent, View.OnClickListener mListener) {
        super(UIUtil.infalte(mContext, R.layout.holder_item, parent));
        textView = itemView.findViewById(R.id.tv_item);
        this.mListener = mListener;
    }


    public void bind(String s) {
        textView.setText(s);
        textView.setTag(s);
        textView.setOnClickListener(mListener);
    }
}
