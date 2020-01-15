package com.bingo.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import androidx.core.content.ContextCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bingo.widget.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wiggins
 */
public class ActionSheetDialog {
    private Context mContext;
    private Dialog mDialog;
    private Display mDisplay;

    private TextView mTextViewTitle;
    private TextView mFirstItemView;

    private LinearLayout mLinearLayoutContent;
    private ScrollView mScrollViewContent;

    private List<SheetItem> sheetItemList;

    private boolean mShowTitle;

    private Point mPoint = new Point();

    public ActionSheetDialog(Context mContext) {
        this.mContext = mContext;
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        mDisplay = windowManager.getDefaultDisplay();
        mDisplay.getSize(mPoint);
    }

    public ActionSheetDialog builder() {
        // 定义Dialog布局和参数
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        mDialog.setContentView(initView());
        Window dialogWindow = mDialog.getWindow();
        if (null != dialogWindow) {
            dialogWindow.setGravity(Gravity.START | Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
        }
        return this;
    }

    @SuppressLint("InflateParams")
    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_actionsheet, null);
        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(mPoint.x);
        // 获取自定义Dialog布局中的控件
        mScrollViewContent = (ScrollView) view.findViewById(R.id.sLayout_content);
        mLinearLayoutContent = (LinearLayout) view.findViewById(R.id.lLayout_content);
        mTextViewTitle = (TextView) view.findViewById(R.id.txt_title);
        TextView cancel = (TextView) view.findViewById(R.id.txt_cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        return view;
    }

    public ActionSheetDialog setTitle(String title) {
        mShowTitle = true;
        mTextViewTitle.setVisibility(View.VISIBLE);
        mTextViewTitle.setText(title);
        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    private boolean isShow() {
        return mDialog.isShowing();
    }

    /**
     * @param strItem 条目名称
     * @param color   条目字体颜色，设置null则默认蓝色
     */
    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color,
                                          OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<>();
        }
        sheetItemList.add(new SheetItem(strItem, color, listener));
        return this;
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        // FIXME 高度控制，非最佳解决办法
        // 添加条目过多的时候控制高度
        if (size >= 7) {
            LayoutParams params = (LayoutParams) mScrollViewContent
                    .getLayoutParams();
            params.height = mPoint.y / 2;
            mScrollViewContent.setLayoutParams(params);
        }

        // 循环添加条目
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = sheetItem.itemClickListener;
//            String[] strArray = mContext.getResources().getStringArray(R.array.chat_dialog_arr);

            TextView textView = new TextView(mContext);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setGravity(Gravity.CENTER);
            textView.setText(strItem);

            //增加有图标的item
            View topView = LayoutInflater.from(mContext).inflate(R.layout.item_topping_menu, null);

            TextView tvTop=topView.findViewById(R.id.tv_topping);


            topView.setBackgroundResource(R.drawable.actionsheet_top_selector);
            tvTop.setText(strItem);
//            if (strItem.equals(strArray[5])) {
//                ivTop.setImageResource(R.drawable.ic_talke_up);
//            } else if (strItem.equals(strArray[6])) {
//                ivTop.setImageResource(R.drawable.ic_talk_down);
//            }

            // 背景图片
            if (size == 1) {
                if (mShowTitle) {
                    textView.setBackgroundResource(R.drawable.action_sheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.action_sheet_single_selector);
                }
            } else {
                if (mShowTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.action_sheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                        mFirstItemView = textView;
                    } else if (i < size) {
                        View view = new View(mContext);
                        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                        view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dialog_selected_bg));
                        mLinearLayoutContent.addView(view);
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        View view = new View(mContext);
                        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                        view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dialog_selected_bg));
                        mLinearLayoutContent.addView(view);
                        textView.setBackgroundResource(R.drawable.action_sheet_bottom_selector);
                    }
                }
            }

            // 字体颜色
            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                        .getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }

            // 高度
            float scale = mContext.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);

            textView.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT, height));
            topView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));

            // 点击事件
            textView.setOnClickListener(v -> {
                listener.onClick(index);
                mDialog.dismiss();
            });

            // 点击事件
            topView.setOnClickListener(v -> {
                listener.onClick(index);
                mDialog.dismiss();
            });

//            if (strItem.equals(strArray[5]) || strItem.equals(strArray[6])) {
//                mLinearLayoutContent.addView(topView);
//            } else {
                mLinearLayoutContent.addView(textView);
//            }
        }
    }

    public void show() {
        setSheetItems();
        mDialog.show();
    }

    public void showAndChangeFirstView() {
        setSheetItems();
        mFirstItemView.setTextColor(Color.parseColor("#bbbbbb"));
        mFirstItemView.setEnabled(false);
        mFirstItemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mDialog.show();
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }


    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");

        private String name;

        SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
