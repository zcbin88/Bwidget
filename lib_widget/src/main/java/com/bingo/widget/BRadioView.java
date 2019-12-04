package com.bingo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * ================================
 *
 * @author: Administrator
 * @email: zcbin2@grgbanking.com
 * @time: 2019/2/19 10:57
 * @version: 1.0
 * @description: 自己画了一个radio控件 用来重复使用
 * =================================
 */
public class BRadioView extends LinearLayout {

    private List<ItemBean> mDatas;

    private Context context;
    private int itemWidth=60;
    private int itemHeight=60;
    //单位dp
    private float divideWidth=20;

    public BRadioView(Context context) {
        super(context);
        this.context=context;
    }

    public BRadioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView(context,attrs);
    }

    public BRadioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView(context,attrs);
    }


    private void initView(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.BRadioView);

        divideWidth = ta.getDimension(R.styleable.BRadioView_rv_divide_width,20);
    }

    /*设置数据*/
    public void setDatas(List<ItemBean> datas) {
        if (datas==null){
            datas = new ArrayList<>();
        }
        this.mDatas = datas;
        //设置LineLayout布局方式为垂直布局，不然的话会只显示第一条数据
        setOrientation(LinearLayout.HORIZONTAL);

        notifyDataSetChanged();
    }
    public ItemBean getData(){
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isCheck){
                return mDatas.get(i);
            }
        }
        return null;
    }


    private void notifyDataSetChanged(){
        removeAllViews();

        if (mDatas ==null || mDatas.size()==0){
            return;
        }


        for (int i=0;i<mDatas.size();i++){
            final int index=i;
            View view =getView(index);
            if (view ==null){
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view,index);
        }
    }

    private View getView(final int position){

        LayoutParams layoutParams =new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.leftMargin=(int)divideWidth/2;
        layoutParams.rightMargin=(int)divideWidth/2;
        layoutParams.weight=1;


        TextView itemTv =  new TextView(context);
        itemTv.setText(mDatas.get(position).getTitle());

        itemTv.setWidth(dp2px(context,itemWidth));
        itemTv.setHeight(dp2px(context,itemHeight));
        itemTv.setLayoutParams(layoutParams);
        itemTv.setGravity(Gravity.CENTER);
        itemTv.setTextSize(15);

        if (mDatas.get(position).isCheck()){
            itemTv.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_rectangle_blue));
            itemTv.setTextColor(ContextCompat.getColor(context,R.color.white));
        }else{
            itemTv.setBackground(ContextCompat.getDrawable(context,R.drawable.border_gray_rectangle));
        }

        itemTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里状态变更事件托管给了onItemClickListener.onItemClick事件
                updateCheckStatus(position);
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position,mDatas.get(position));
                }

            }
        });

        return itemTv;
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


    /**
     * 置选中的项
     * @param id
     * @param title
     */
    public void setCheck(String id,String title){
        for (int i=0 ;i<mDatas.size();i++){
            if (mDatas.get(i).getId().equals(id) || mDatas.get(i).getTitle().equals(title)){
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
        void onItemClick(int position, ItemBean item);
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






    public static class ItemBean{
        private String title;
        private String id;
        private boolean isCheck;

        public ItemBean() {
        }

        public ItemBean(String title, boolean isCheck) {
            this.title = title;
            this.isCheck = isCheck;
        }

        public ItemBean(String id,String title, boolean isCheck) {
            this.id=id;
            this.title = title;
            this.isCheck = isCheck;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
}
