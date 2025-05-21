package com.bingo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


/**
 * ================================
 *
 * @author: zcbin2
 * @email: zcbin2@grgbanking.com
 * @time: 2019/5/16 16:35
 * @version: 1.0
 * @description: 封装带有label的TextView
 * =================================
 */
public class BInputView extends LinearLayout {
    private Context mContext;
    private LayoutInflater layoutInflater;

    private int shape;
    private String label;
    private float labelTextSize;//sp
    private int labelTextColor = 0xFFD9D9D9;
    float labelWidth = 0.0f;
    private boolean labelBold = false;
    private String content;
    private String contentId;
    private String hint;
    private boolean required;
    private int inputType;
    private int valueTextColor;
    private float valueTextSize;
    private int valueMinHeight;
    //TextView
    public static final int SHAPE_TEXT = 0;
    //TextView 选择时间
    public static final int SHAPE_DATE = 1;
    //TextView 下拉选择
    public static final int SHAPE_SELECT = 2;
    //TextView 带有右箭头 更多
    public static final int SHAPE_RIGHT = 3;
    //EditText
    public static final int SHAPE_EDIT = 4;

    public static final int INPUT_NUMBER = 0;
    public static final int INPUT_NUMBERSIGNED = 1;
    public static final int INPUT_NUMBERDECIMAL = 2;
    public static final int INPUT_PHONE = 3;
    public static final int INPUT_DATETIME = 4;
    public static final int INPUT_DATE = 5;
    public static final int INPUT_TIME = 6;
    public static final int INPUT_TEXT = 7;

    TextView tvRequired;
    TextView tvLabel;
    TextView tvValue;
    EditText etValue;
    TextWatcher textWatcher;
    LinearLayout rowLayout;

    public BInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttr(attrs);
        initView();
    }

    public BInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getAttr(attrs);
        initView();
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BInputView);
        shape = typedArray.getInt(R.styleable.BInputView_bshape, SHAPE_TEXT);
        hint = typedArray.getString(R.styleable.BInputView_bhint);
        required = typedArray.getBoolean(R.styleable.BInputView_brequired, false);
        label = typedArray.getString(R.styleable.BInputView_blabel);
        labelTextColor = typedArray.getColor(R.styleable.BInputView_blabel_text_color, Color.GRAY);
        labelWidth = typedArray.getDimension(R.styleable.BInputView_blabel_width, labelWidth);
        content = typedArray.getString(R.styleable.BInputView_bvalue);
        inputType = typedArray.getInt(R.styleable.BInputView_binput_type, INPUT_TEXT);
        labelBold = typedArray.getBoolean(R.styleable.BInputView_blabel_bold, labelBold);
        labelTextSize = typedArray.getDimensionPixelSize(R.styleable.BInputView_blabel_text_size, sp2px(getContext(), 14));
        valueTextColor = typedArray.getColor(R.styleable.BInputView_bvalue_text_color, Color.GRAY);
        valueTextSize = typedArray.getDimensionPixelSize(R.styleable.BInputView_bvalue_text_size, sp2px(getContext(), 13));
        valueMinHeight = typedArray.getDimensionPixelSize(R.styleable.BInputView_bvalue_min_height, 30);//默认30
    }

    private void initView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(getContext());
        }
        View view = layoutInflater.inflate(R.layout.view_text_customer, null, false);
        tvRequired = view.findViewById(R.id.tv_required);
        tvLabel = view.findViewById(R.id.tv_label);
        tvValue = view.findViewById(R.id.tv_value);
        etValue = view.findViewById(R.id.et_value);
        rowLayout = view.findViewById(R.id.row_layout);
        initShape();

        tvRequired.setVisibility(required ? View.VISIBLE : View.GONE);

//        tvLabel.setBoldText(labelBold);
        tvLabel.setText(label);
        if (labelTextSize > 0) {
            tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize);
        }
        tvLabel.setTextColor(labelTextColor);
        if (labelWidth > 0.0f) {
            tvLabel.setWidth(dp2px(getContext(), labelWidth));
        }

        tvValue.setText(content);
        tvValue.setHint(hint);
        tvValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueTextSize);
        etValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueTextSize);
        tvValue.setTextColor(valueTextColor);
        tvValue.setMinHeight(dp2px(getContext(),valueMinHeight));
        etValue.setMinHeight(dp2px(getContext(),valueMinHeight));
        etValue.setTextColor(valueTextColor);

        etValue.setText(content);
        etValue.setHint(hint);
        if (textWatcher != null) {
            etValue.addTextChangedListener(textWatcher);
        }

        addView(view, layoutParams);
    }

    private void initShape() {

        tvValue.setVisibility(shape == SHAPE_EDIT ? View.GONE : View.VISIBLE);
        etValue.setVisibility(shape == SHAPE_EDIT ? View.VISIBLE : View.GONE);

        switch (shape) {
            case SHAPE_TEXT:
                break;
            case SHAPE_EDIT:
                break;
            case SHAPE_DATE:
                Drawable dra = ContextCompat.getDrawable(mContext, R.drawable.ic_calendar);
                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                tvValue.setCompoundDrawables(null, null, dra, null);
                tvValue.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_gray_rectangle));
                break;
            case SHAPE_SELECT:
                Drawable select = ContextCompat.getDrawable(mContext, R.drawable.ic_dropdown);
                select.setBounds(0, 0, select.getMinimumWidth(), select.getMinimumHeight());
                tvValue.setCompoundDrawables(null, null, select, null);
                tvValue.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_gray_rectangle));
                break;
            case SHAPE_RIGHT:
                Drawable right = ContextCompat.getDrawable(mContext, R.drawable.ic_right_next);
                right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
                tvValue.setCompoundDrawables(null, null, right, null);
                break;
            default:
                break;
        }

        switch (inputType) {
            case INPUT_NUMBER:
                etValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case INPUT_NUMBERSIGNED:
                etValue.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
                break;
            case INPUT_NUMBERDECIMAL:
                etValue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case INPUT_PHONE:
                etValue.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case INPUT_DATETIME:
                etValue.setInputType(InputType.TYPE_CLASS_DATETIME);
                break;
            case INPUT_DATE:
                etValue.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                break;
            case INPUT_TIME:
                etValue.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                break;
            case INPUT_TEXT:
                etValue.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            default:
                break;
        }

    }


    private void updateView() {
        removeAllViews();
        initView();
    }

    public void setShape(int shape) {
        this.shape = shape;
        updateView();
    }

    public void setLabel(String label) {
        this.label = label;
        updateView();
    }

    public String getLabel() {
        return label == null ? "" : label.replace(":", "");
    }

    public void setHint(String hint) {
        this.hint = hint;
        updateView();
    }

    public void setRequired(boolean required) {
        this.required = required;
        updateView();
    }

    public boolean isRequired() {
        return required;
    }


    public TextWatcher getTextWatcher() {
        return textWatcher;
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
        if (etValue != null) {
            etValue.addTextChangedListener(textWatcher);
        }
    }

    /**
     * 是否必填
     *
     * @return true 必填 false 非必填
     */
    public boolean isNonNull() {
        return required;
    }

    /**
     * 获取输入的内容
     *
     * @return
     */
    public String getContent() {
        if (shape == SHAPE_EDIT) {
            return etValue.getText().toString().trim();
        }
        return tvValue.getText().toString().trim();
    }

    /**
     * TextView/EditText 赋值
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? "" : content.trim();
        if (shape == SHAPE_EDIT) {
            etValue.setText(content == null ? "" : content.trim());
        } else {
            tvValue.setText(content == null ? "" : content.trim());
        }
    }

    public String getContentId() {
        return contentId == null ? "" : contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    OnGvListener onGvListener;

    private void initListener() {
        tvValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onGvListener != null) {
                    onGvListener.click();
                }
            }
        });
    }

    public void addGvListener(OnGvListener onGvListener) {
        this.onGvListener = onGvListener;
        initListener();
    }

    public void removeGvListener() {
        this.setOnClickListener(null);
        if (this.onGvListener != null) {
            this.onGvListener = null;
        }
    }

    public interface OnGvListener {
        void click();
    }

    public static int dp2px(Context context, float dpValue) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } else {
            return 0;
        }
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
