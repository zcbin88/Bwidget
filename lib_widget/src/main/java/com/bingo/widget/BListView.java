package com.bingo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingo.widget.model.ItemListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================
 *
 * @author: Administrator
 * @email: zcbin2@grgbanking.com
 * @time: 2018/5/21 14:06
 * @version: 1.0
 * @description:
 * =================================
 */
public class BListView extends LinearLayout {

    private List<ItemListBean> mDatas;
    private LayoutInflater layoutInflater;

    /*设置数据*/
    public void setDatas(List<ItemListBean> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.mDatas = datas;
        //设置LineLayout布局方式为垂直布局，不然的话会只显示第一条数据
        BListView.this.setOrientation(LinearLayout.VERTICAL);

        notifyDataSetChanged();
    }

    public BListView(Context context) {
        super(context);
    }

    public BListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void notifyDataSetChanged() {
        removeAllViews();

        if (mDatas == null || mDatas.size() == 0) {
            return;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mDatas.size(); i++) {
            final int index = i;
            View view = getView(index);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }
    }


    private View getView(final int position) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(getContext());
        }

        View convertView = layoutInflater.inflate(R.layout.item_list_view, null, false);
        TextView titleTv = convertView.findViewById(R.id.title_Tv);
        titleTv.setText(mDatas.get(position).getTitle());
        ImageView ivSelected = convertView.findViewById(R.id.iv_selected);
        if (mDatas.get(position).isCheck()) {
            ivSelected.setVisibility(View.VISIBLE);
        } else {
            ivSelected.setVisibility(View.GONE);
        }


        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateCheckStatus(position);
                onItemClickListener.onItemClick(position);
            }
        });

        return convertView;
    }


    public void updateCheckStatus(int position) {
        for (int i = 0; i < mDatas.size(); i++) {
            if (i == position) {
                mDatas.get(i).setCheck(true);
            } else {
                mDatas.get(i).setCheck(false);
            }
        }
        notifyDataSetChanged();
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
