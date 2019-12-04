package com.bingo.widget.dialog.picker;

import android.view.View;


import com.bingo.widget.R;


import java.util.Map;

/**
 * Created by zcb on 2017/4/10.
 * 二级范围值联动：
 * 布局文件源于 "R.layout.layout_two_column_linkage"  不可空，要修改 id 请对应修改此类中获取 id 的信息
 * NumberPickerView 源于
 *
 * @see NumberPickerView
 * 使用示例：
 * public View getRangePickerView() {
 * View view = View.inflate(this, R.layout.layout_two_column_linkage, null);
 * rangePickerView = new RangePickerView(view, datas, new RangePickerView.OnDataChangeListener() {
 * //@Override
 * public void onDataChange(String left, String right) {
 * tvRangeMsg.setText(left + " - " + right);
 * }
 * });
 * return view;
 * }
 * 在任意要显示该控件的父容器 .addView(getRangePickerView()); 即可
 */

public class RangePickerView {

    private View view;
    private NumberPickerView npvLeft;
    private NumberPickerView npvRight;
    private OnDataChangeListener onDataChangeListener;
    private String[] leftDatas;
    private String[] datasId;
    private Map<String, String> dataIdMap;
    private int defaultIndexLeft = 0;
    private int defaultIndexRight = 0;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public RangePickerView(View view, String[] datas, String[] datasId, Map<String, String> dataIdMap, String currentLeftId, String currentRightId, OnDataChangeListener listener) {
        this.view = view;
        this.leftDatas = datas;
        this.datasId = datasId;
        this.dataIdMap = dataIdMap;

        /*计算 左右两列的初始下标*/
        int indexRight = 0;
        for (int i = 0; i < datasId.length; i++) {
            if (datasId[i].equals(currentLeftId)) {
                defaultIndexLeft = i;
            }
            if (datasId[i].equals(currentRightId)) {
                indexRight = i;
            }
        }
        /* 右边数据起始大于左边数据当前值 */
        int rightStart = defaultIndexLeft;
        /* 由 datas index 转换成  删减后的 rightDatas index*/
        this.defaultIndexRight = indexRight - rightStart;

        this.onDataChangeListener = listener;
        initView(view);

        refreshRightData(rightStart);
    }

    /**
     * @param index 左侧当前 index ； 为了计算右侧数据 ; 右侧数据第一行的 “不限” 一直保留
     */
    private void refreshRightData(int index) {
        int size = leftDatas.length - index;
        if (size == 0) {
            size = 1;
        }
        String[] rightDatas = new String[size];
        System.arraycopy(leftDatas, index, rightDatas, 0, size);
        //“不限”一直保留
        if (rightDatas.length > 0 & leftDatas.length > 0) {
            if( leftDatas[0].equals("不限") | leftDatas[0].equals("未透露") ) {
                rightDatas[0] = leftDatas[0];
            }
        }
        //改变前右侧默认选中值
        String rightValue = npvRight.getContentByCurrValue();
        for (int i = 0; i < rightDatas.length; i++) {
            if (rightValue.equals(rightDatas[i])) {
                defaultIndexRight = i;
            }
        }
        setNumberPicker(npvRight, rightDatas, defaultIndexRight);
    }

    private void initView(View view) {
        npvLeft = (NumberPickerView) view.findViewById(R.id.npv_left);
        npvRight = (NumberPickerView) view.findViewById(R.id.npv_right);
        npvLeft.setOnValueChangeListenerInScrolling(onValueChangeListenerInScrolling);
        npvRight.setOnValueChangeListenerInScrolling(onValueChangeListenerInScrolling);
        setNumberPicker(npvLeft, leftDatas, defaultIndexLeft);
    }

    private void setNumberPicker(NumberPickerView picker, String[] datas, int index) {
        picker.updateContentAndIndex(datas);
        picker.setMinValue(0);
        picker.setMaxValue(datas.length - 1);
        /* 当左边数据默认为最后一个值时 ，右边数据默认开始下标不再是 = 左边 + 1 。故在此限值  */
        if (index >= datas.length) {
            index = datas.length - 1;
        }
        picker.setValue(index);
    }

    public String getLeftValue() {
        return npvLeft.getContentByCurrValue();
    }

    public String getRightValue() {
        return npvRight.getContentByCurrValue();
    }

    private NumberPickerView.OnValueChangeListenerInScrolling onValueChangeListenerInScrolling = new NumberPickerView.OnValueChangeListenerInScrolling() {

        @Override
        public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
            if (picker.getId()==R.id.npv_left){
                defaultIndexRight = npvRight.getValue();
                refreshRightData(newVal);
            }

            if (onDataChangeListener != null) {
                onDataChangeListener.onDataChange(getLeftValue(), dataIdMap.get(getLeftValue()), getRightValue(), dataIdMap.get(getRightValue()), false);
            }
        }
    };

    public interface OnDataChangeListener {
        /**
         * @param confirm 备用字段，用在 dialog 的确认键
         */
        void onDataChange(String left, String leftId, String right, String rightId, boolean confirm);
    }
}
