package com.bingo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bingo.widget.model.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================
 *
 * @author: Administrator
 * @email: zcbin2@grgbanking.com
 * @time: 2019/2/18 10:59
 * @version: 1.0
 * @description: 自定义了一个假的tab view
 * =================================
 */
public class BTabView extends LinearLayout {

    private List<ItemBean> mDatas;
    private LayoutInflater layoutInflater;
    private Context context;

    /*设置数据*/
    public void setDatas(List<ItemBean> datas) {
        if (datas==null){
            datas = new ArrayList<>();
        }
        this.mDatas = datas;
        //设置LineLayout布局方式为垂直布局，不然的话会只显示第一条数据
        BTabView.this.setOrientation(LinearLayout.VERTICAL);

        notifyDataSetChanged();
    }
    public BTabView(Context context) {
        super(context);
        this.context=context;
    }

    public BTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public BTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }


    private void notifyDataSetChanged(){
        removeAllViews();

        if (mDatas ==null || mDatas.size()==0){
            return;
        }

        LayoutParams layoutParams =new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<mDatas.size();i++){
            final int index=i;
            View view =getView(index);
            if (view ==null){
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view,index,layoutParams);
        }
    }


    private View getView(final int position){
        if (layoutInflater ==null){
            layoutInflater = LayoutInflater.from(getContext());
        }

        View convertView = layoutInflater.inflate(R.layout.item_tab_view,null,false);

        TextView titleTv =  convertView.findViewById(R.id.tv_tab_title);
        Drawable drawable = ContextCompat.getDrawable(context,mDatas.get(position).getIcon());
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        titleTv.setCompoundDrawables(drawable,null,null,null);
        titleTv.setCompoundDrawablePadding(dp2px(context,15));

        RelativeLayout itamTabLayout=convertView.findViewById(R.id.item_tab_layout);

        titleTv.setText(mDatas.get(position).getTitle());

        if (mDatas.get(position).isCheck()){
            itamTabLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_memberinfo_choose));
        }else{
            itamTabLayout.setBackground(null);
        }

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里状态变更事件托管给了onItemClickListener.onItemClick事件
//                updateCheckStatus(position);
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }

            }
        });

        return convertView;
    }

    public void updateCheckStatus(int position){
        for (int i=0 ;i<mDatas.size();i++){
            if (i==position){
                mDatas.get(i).setCheck(true);
            }else{
                mDatas.get(i).setCheck(false);
            }
        }
        notifyDataSetChanged();
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } else {
            return 0;
        }
    }


}
