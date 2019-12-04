package com.bingo.widget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bingo.widget.FontTextView;
import com.bingo.widget.R;


public class AlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout llayoutBg;
    private FontTextView txtTitle;
    private TextView txtMsg;
    private Button btnNeg;
    private Button btnPos;
    private Button btnMore;
    private View imgLine;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private boolean showMoreBtn = false;

    public AlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        llayoutBg = view.findViewById(R.id.lLayout_bg);
        txtTitle = view.findViewById(R.id.txt_title);
        txtTitle.setVisibility(View.GONE);
        txtMsg = view.findViewById(R.id.txt_msg);
        txtMsg.setVisibility(View.GONE);
        btnNeg = view.findViewById(R.id.btn_neg);
        btnNeg.setVisibility(View.GONE);
        btnPos = view.findViewById(R.id.btn_pos);
        btnPos.setVisibility(View.GONE);
        imgLine = view.findViewById(R.id.img_line);
        imgLine.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        llayoutBg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.65), LayoutParams.WRAP_CONTENT));

        return this;
    }

    /**
     * @param isMore 是否显示更多按钮
     */
    public AlertDialog builder(boolean isMore) {
        if (isMore) {
            // 获取Dialog布局
            View view = LayoutInflater.from(context).inflate(
                    R.layout.view_alertdialog_more, null);

            // 获取自定义Dialog布局中的控件
            llayoutBg = view.findViewById(R.id.lLayout_bg);
            txtTitle = view.findViewById(R.id.txt_title);
            txtTitle.setVisibility(View.GONE);
            txtMsg = view.findViewById(R.id.txt_msg);
            txtMsg.setVisibility(View.GONE);
            btnNeg = view.findViewById(R.id.btn_one);
            btnNeg.setVisibility(View.GONE);
            btnPos = view.findViewById(R.id.btn_two);
            btnPos.setVisibility(View.GONE);
            btnMore = view.findViewById(R.id.btn_three);
            btnMore.setVisibility(View.GONE);
            imgLine = view.findViewById(R.id.img_line);
            imgLine.setVisibility(View.GONE);

            // 定义Dialog布局和参数
            dialog = new Dialog(context, R.style.AlertDialogStyle);
            dialog.setContentView(view);

            // 调整dialog背景大小
            llayoutBg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                    .getWidth() * 0.65), LayoutParams.WRAP_CONTENT));
            return this;
        } else {
            return builder();
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        if (dialog == null) {
            return;
        }
        dialog.setOnDismissListener(listener);
    }

    public AlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txtTitle.setText("标题");
        } else {
            txtTitle.setText(title);
        }
        return this;
    }

    public AlertDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txtMsg.setText("内容");
        } else {
            txtMsg.setText(msg);
        }
        return this;
    }

    /**
     * 为版本更新定制
     *
     * @return
     */
    public AlertDialog setUpdateMsg(String msg) {
        showMsg = true;
        txtMsg.setGravity(Gravity.LEFT);
        txtMsg.setLineSpacing(0.0f, 1.1f);
        txtMsg.setText(msg);
        return this;
    }

    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertDialog setPositiveButton(String text,
                                         final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btnPos.setText("确定");
        } else {
            btnPos.setText(text);
        }
        btnPos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * @param needDismiss 点击是否需要 dismiss
     */
    public AlertDialog setPositiveButton(String text,
                                         final boolean needDismiss,
                                         final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btnPos.setText("确定");
        } else {
            btnPos.setText(text);
        }
        btnPos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                if (needDismiss) {
                    dialog.dismiss();
                }
            }
        });
        return this;
    }

    public AlertDialog setNegativeButton(String text,
                                         final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btnNeg.setText("取消");
        } else {
            btnNeg.setText(text);
        }
        btnNeg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertDialog setMoreButton(String text,
                                     final OnClickListener listener) {
        showMoreBtn = true;
        if ("".equals(text)) {
            btnMore.setText("");
        } else {
            btnMore.setText(text);
        }
        btnMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txtTitle.setText("提示");
            txtTitle.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txtTitle.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txtMsg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btnPos.setText("确定");
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.alert_dialog_single_selector);
            btnPos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.alert_dialog_right_selector);
            btnNeg.setVisibility(View.VISIBLE);
            btnNeg.setBackgroundResource(R.drawable.alert_dialog_left_selector);
            imgLine.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.alert_dialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btnNeg.setVisibility(View.VISIBLE);
            btnNeg.setBackgroundResource(R.drawable.alert_dialog_single_selector);
        }
        if (showMoreBtn) {
            btnMore.setVisibility(View.VISIBLE);
            btnMore.setBackgroundResource(R.drawable.alert_dialog_single_selector);
        }
    }

    public void show() {
        if (null != context) {
            setLayout();
            dialog.show();
//            if (context instanceof BaseActivity) {
//                if (!((BaseActivity) context).isFinishing()) {
//
//                }
//            }
        }

    }

    public boolean isShowing() {
        return dialog.isShowing();
    }
}
