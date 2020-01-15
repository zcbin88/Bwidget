package com.bingo.demo.adapter;

import android.content.Context;

import com.bingo.demo.R;
import com.bingo.demo.bean.MenuEntity;
import com.bingo.widget.gridview.BaseGridViewAdapter;

import java.util.List;

/**
 * ================================
 *
 * @author: zcb
 * @email: zhang-cb@foxmail.com
 * @time: 2018/5/18 10:07
 * @version: 1.0
 * @description: 会员特权 适配器
 * =================================
 */
public class MemberPrivilegeAdapter extends BaseGridViewAdapter<MenuEntity> {
    private Context context;
    private List<MenuEntity> datas;


    public MemberPrivilegeAdapter(List<MenuEntity> mData, int mLayoutRes) {
        super(mData, mLayoutRes);
    }

    @Override
    public void bindView(ViewHolder holder, MenuEntity obj, int position) {
        holder.setText(R.id.privilege_title_tv, obj.getName());
    }
}
