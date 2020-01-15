package com.bingo.widget.gridview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bingo.widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================
 *
 * @author: zcb
 * @email: zcbin2@grgbanking.com
 * @time: 2018/8/29 10:46
 * @version: 1.0
 * @description: =================================
 */
public abstract class BaseGridViewAdapter<T> extends BaseAdapter {

    private Context context;

    private List<T> mData;

    //布局id
    private int mLayoutRes;
    /**
     * 选中时的背景
     */
    private int itemSelectedBackround=-1;
    /**
     * 未选中时的背景
     */
    private int itemUnselectedBackround=-1;


    /**
     * 是否使用item背景
     */
    private boolean useBackground=true;


    private int clickTemp=-1;


    public BaseGridViewAdapter(List<T> mData, int mLayoutRes) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
    }
    public BaseGridViewAdapter(List<T> mData, int mLayoutRes,int itemSelectedBackround,int itemUnselectedBackround) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
        this.itemSelectedBackround=itemSelectedBackround;
        this.itemUnselectedBackround=itemUnselectedBackround;
    }
    public BaseGridViewAdapter(List<T> mData, int mLayoutRes,boolean useBackground) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
        this.useBackground=useBackground;
    }

    public void setSeclection(int position) {
        clickTemp = position;
    }

    /**
     * 默认选中
     * @param position
     */
    public void setDefaultSelection(int position){
        if (clickTemp==-1){
            setSeclection(position);
        }
    }

    public int getSelection(){
        return clickTemp;
    }



    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes
                , position);
        bindView(holder, getItem(position),position);


        bindViewClickListener(holder);

        if (!useBackground){
            return holder.getItemView();
        }

        if (clickTemp==position){
            holder.getItemView().setBackgroundResource(itemSelectedBackround==-1? R.drawable.bg_grid_item_red:itemSelectedBackround);
        }else{
            holder.getItemView().setBackgroundResource(itemUnselectedBackround==-1?R.drawable.bg_grid_item_gray:itemUnselectedBackround);
        }

        return holder.getItemView();
    }


    private void bindViewClickListener(final ViewHolder holder){
        if (holder==null){
            return;
        }
        final View view=holder.getItemView();
        if (view==null){
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnItemClickListener()!=null && holder!=null){
                    getOnItemClickListener().onItemClick(BaseGridViewAdapter.this,view,holder.getItemPosition());
                }
            }
        });
    }

    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(BaseGridViewAdapter adapter, View view, int position);
    }

    public abstract void bindView(ViewHolder holder, T obj,int position);




    //添加一个元素
    public void add(T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<T> datas) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mData==null? new ArrayList<T>() :mData;
    }


    //往特定位置，添加一个元素
    public void add(int position, T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        if (mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder {

        private SparseArray<View> mViews;   //存储ListView 的 item中的View
        private View item;                  //存放convertView
        private int position;               //游标
        private Context context;            //Context上下文

        //构造方法，完成相关初始化
        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        //绑定ViewHolder与item
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                                      int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mViews.put(id, t);
            }
            return t;
        }


        /**
         * 获取当前条目
         */
        public View getItemView() {
            return item;
        }

        /**
         * 获取条目位置
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * 设置文字
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * 设置图片
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }


        /**
         * 设置点击监听
         */
        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        /**
         * 设置可见
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        /**
         * 设置标签
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }

        //其他方法可自行扩展

    }


}
