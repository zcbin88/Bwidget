package com.bingo.demo.adapter;

import android.content.Context;

import com.bingo.demo.R;
import com.bingo.demo.bean.MainItemBean;
import com.bingo.widget.gridview.BaseGridViewAdapter;

import java.util.List;

/**
 * ================================
 *
 * @author: zcbin2
 * @email: zcbin2@grgbanking.com
 * @time: 2019/12/5 15:30
 * @version: 1.0
 * @description: =================================
 */
public class GridAdapter extends BaseGridViewAdapter<MainItemBean> {

    Context context;

    public GridAdapter(List<MainItemBean> mData, Context context) {
        super(mData, R.layout.item_grid_main);
        this.context=context;
    }

    @Override
    public void bindView(ViewHolder holder, MainItemBean obj, int position) {
        holder.setText(R.id.valid_time_Tv,obj.getTitle());
    }
}
