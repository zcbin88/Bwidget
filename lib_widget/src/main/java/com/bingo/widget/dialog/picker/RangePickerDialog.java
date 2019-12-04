package com.bingo.widget.dialog.picker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.bingo.widget.R;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * ================================
 *
 * @author: zcb
 * @email: zhang-cb@foxmail.com
 * @time: 2019/4/28 13:54
 * @version: 1.0
 * @description: 弹出范围值选择器 dialog
 * =================================
 */
public class RangePickerDialog extends Dialog implements View.OnClickListener {

    private RangePickerView rangePickerView;
    TextView tvCancel;
    TextView tvConfirm;
    TextView tvTitle;
    FrameLayout frameLayoutPicker;

    private Context mContext;
    private String title;
    private String[] datas;
    private String[] dataIds;
    private Map<String, String> dataIdMap = new ArrayMap<>();
    private String defaultLeftId;
    private String defaultRightId;
    private RangePickerView.OnDataChangeListener onDataChangeListener;

    public RangePickerDialog(@NonNull Context context, String title, String[] datas, String[] dataIds, String currentLeftId, String currentRightId, RangePickerView.OnDataChangeListener onDataChangeListener) {
        super(context, R.style.ActionSheetDialogStyle);
        if (currentLeftId == null) {
            currentLeftId = "";
        }
        if (currentRightId == null) {
            currentRightId = "";
        }
        this.mContext = context;
        this.title = title;
        this.datas = datas;
        this.dataIds = dataIds;
        /* 初始化 数据源与id 的绑定关系 */
        for (int i = 0; i < datas.length; i++) {
            dataIdMap.put(datas[i], dataIds[i]);
        }
        this.defaultLeftId = currentLeftId;
        this.defaultRightId = currentRightId;
        this.onDataChangeListener = onDataChangeListener;
    }

    public RangePickerDialog(@NonNull Context context, String title, Map<String, String> map, String currentLeftId, String currentRightId, RangePickerView.OnDataChangeListener onDataChangeListener) {
        super(context, R.style.ActionSheetDialogStyle);
        if (currentLeftId == null) {
            currentLeftId = "";
        }
        if (currentRightId == null) {
            currentRightId = "";
        }
        this.mContext = context;
        this.title = title;
        this.defaultLeftId = currentLeftId;
        this.defaultRightId = currentRightId;
        this.onDataChangeListener = onDataChangeListener;
        //将 map 转换成 id 跟 data 两个数组
        dataIds = new String[map.size()];
        datas = new String[map.size()];
        Set<String> keys = map.keySet();
        Iterator iterator = keys.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String next = iterator.next() + "";
            dataIds[i] = next;
            datas[i] = map.get(next);
            dataIdMap.put(map.get(next), next);
            i++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picker);
        tvCancel = findViewById(R.id.tv_cancel_dialog);
        tvConfirm = findViewById(R.id.tv_confirm_dialog);
        tvTitle = findViewById(R.id.tv_title_dialog);
        frameLayoutPicker = findViewById(R.id.fl_picker);


        Window win = getWindow();
        win.setGravity(Gravity.BOTTOM);
        win.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init() {
        tvTitle.setText(title);
        frameLayoutPicker.addView(getPickerView(mContext, datas, onDataChangeListener));

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        dismiss();
        if (view.getId() == R.id.tv_cancel_dialog) {
            return;
        }
        if (view.getId() == R.id.tv_confirm_dialog && rangePickerView != null & onDataChangeListener != null) {
            onDataChangeListener.onDataChange(rangePickerView.getLeftValue(),
                    dataIdMap.get(rangePickerView.getLeftValue()),
                    rangePickerView.getRightValue(),
                    dataIdMap.get(rangePickerView.getRightValue()),
                    true);
            return;
        }
    }


    /**
     * 在任意要显示该控件的父容器 .addView(getCityPickerView()); 即可显示该选择器
     */
    private View getPickerView(Context context, String[] datas, RangePickerView.OnDataChangeListener onDataChangeListener) {
        View view = View.inflate(context, R.layout.layout_two_column_linkage, null);
        rangePickerView = new RangePickerView(view, datas, dataIds, dataIdMap, defaultLeftId, defaultRightId, onDataChangeListener);
        return view;
    }


}
