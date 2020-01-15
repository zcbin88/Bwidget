package com.bingo.widget.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.widget.TextView;

import com.bingo.widget.R;
import com.bingo.widget.gridview.BaseGridViewAdapter;

import java.util.List;

/**
 * ================================
 *
 * @author: Administrator
 * @email: zcbin2@grgbanking.com
 * @time: 2018/12/26 13:58
 * @version: 1.0
 * @description:
 * =================================
 */
public class ConstellationGridViewAdapter extends BaseGridViewAdapter<ConstellationGridViewAdapter.ItemBean> {

    private List<ItemBean> mData;
    private Context context;

    private TextView tv;

    public ConstellationGridViewAdapter(Context context, List<ItemBean> mData) {
        super(mData, R.layout.item_constellation_grid,false);
        this.mData=mData;
        this.context=context;
    }

    @Override
    public void bindView(ViewHolder holder, ItemBean obj, int position) {
        tv=holder.getView(R.id.name_tv);
        tv.setText(obj.getName());
        if (obj.isSelected()){
            tv.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_grid_item_selected));
            tv.setTextColor(ContextCompat.getColor(context,R.color.color_red_fb));
        }else{
            tv.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_grid_item_unselected));
            tv.setTextColor(ContextCompat.getColor(context,R.color.gray_7c7c7c));
        }
    }



    public static class ItemBean{
        public ItemBean() {
        }

        public ItemBean(String id,String name) {
            this.id=id;
            this.name = name;
        }
        public ItemBean(String id,String name,boolean selected) {
            this.id=id;
            this.name = name;
            this.selected=selected;
        }

        private String name;
        private String id;
        boolean selected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
