package com.bingo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;



/**
 * ================================
 *
 * @author: zcb
 * @email: zcbin2@grgbanking.com
 * @time: 2019/4/28 13:52
 * @version: 1.0
 * @description: 自定义字体 TextView
 * * xml 定义方式
 * =================================
 */

public class FontTextView extends androidx.appcompat.widget.AppCompatTextView {

    private int mLineY;

    private int mViewWidth;

    public static final String TWO_CHINESE_BLANK = "  ";


    private StringBuffer mText;
    private StringBuffer newText = null;
    private Paint mPaint;
    /**
     * VIEW的高度
     */
    private int mHeight = 0;
    /**
     * 行高
     */
    private static final int LINE_HEIGHT = 40;
    /**
     * 显示的字数
     */
    private int numberOfWords;

    private boolean isFull;

    /**
     * 苹方常规  细
     */
    public final static int FONT_TYPE_REGULAR = 1;
    public final static int FONT_TYPE_HELVETICA = 2;
    /**
     * 占卜
     */
    public final static int FONT_TYPE_DIVINATION = 3;

    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        //由于字体库太大，无故增加 apk 大小。这里将不设置字体了
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        isFull = a.getBoolean(R.styleable.FontTextView_font_full, false);
        boolean bold = a.getBoolean(R.styleable.FontTextView_font_bold, false);
        setBoldText(bold);
        int fontType = a.getInteger(R.styleable.FontTextView_font_type, 0);
        setTypefaceType(fontType);
        a.recycle();
    }


    /**
     * 设置字体
     *
     * @param type 字体类型{@link FontTextView}
     */
    public void setTypefaceType(int type) {

    }

    /**
     * 设置字体是否为粗体
     *
     * @param bold true 为粗
     */
    public void setBoldText(boolean bold) {
        TextPaint tp = getPaint();
        tp.setFakeBoldText(bold);
    }

    @Override
    public void setWidth(int width) {
        if (this.getLayoutParams().width == width) {
            return;
        }
        this.getLayoutParams().width = width;
        this.requestLayout();
    }


    /**
     * 以下代码是为了解决TextView未满一行就换行的情况
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isFull) {
            // 获取文本内容
            String text = getText().toString();
            //以单例模式对文字进行拆分
            if (null == mText) {
                mText = new StringBuffer(text);
                //获取画笔
                TextPaint paint = getPaint();
                // 获取文字颜色将其设置到画笔上
                paint.setColor(getCurrentTextColor());
                //设置文字大小
                paint.setTextSize(getTextSize());
                //设置字体，包括字体的类型，粗细，还有倾斜、颜色等
                paint.setTypeface(getTypeface());
                paint.drawableState = getDrawableState();
                //获取填写字数的宽
                mViewWidth = getMeasuredWidth();
                mPaint = paint;
                //对文字进行分行处理
                caculateChangeLine();
            }
            //设置头部内边距
            mLineY = getPaddingTop();
            mLineY += getTextSize();
            //避免出现空视图
            Layout layout = getLayout();

            if (layout == null) {
                return;
            }
            Paint.FontMetrics fm = mPaint.getFontMetrics();

            int textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
            textHeight = (int) (textHeight * layout.getSpacingMultiplier() + layout
                    .getSpacingAdd());//获取文字的高度
            //将分割好滴文字进行排版
            String[] split = newText.toString().split("\n");
            //此处设置文本显示的高度，适配一些手机无法显示
            if (split.length > 0) {
                //多设置了几行以避免显示不全(看情况进行修改)
                int i = (split.length);
                int setheight = textHeight * i;
                //设置textview高度
                setHeight(setheight);
            }

            for (String string : split) {
                //此处为源例子上的写法，标点符号换行问题还是存在（楼主引用，ToDBC(aaa)的方法进行了修改，已解决这个bug）
                float width = StaticLayout.getDesiredWidth(string, 0,
                        string.length(), getPaint());

                if (TextUtils.isEmpty(string)) {
                    continue;
                }
                //验证是否足够一个屏幕的宽度
                int strWidth = (int) mPaint.measureText(string + "好好");
                //判断是否足够一行显示的字数，足够久进行字的处理不够则直接画出来
                if (needScale(string) && string.trim().length() > numberOfWords - 5 && mViewWidth < strWidth)
                //，避免出现字数不够，字间距被画出来的字间距过大影响排版
                {// 判断是否结尾处需要换行，并且不是文本最后一行
                    drawScaledText(canvas, getPaddingLeft(), string, width);
                } else {
                    // 将字符串直接画到控件上
                    canvas.drawText(string, getPaddingLeft(), mLineY, mPaint);
                }
                mLineY += textHeight;
            }
        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * 计算出一行显示的文字
     */
    private String caculateOneLine(String str) {
        //对一段没有\n的文字进行换行
        String returnStr;
        int strWidth = (int) mPaint.measureText(str);
        int len = str.length();
        //大概知道分多少行
        int lineNum;
        if (mViewWidth == 0) {
            lineNum = 0;
        } else {
            lineNum = strWidth / mViewWidth;
        }

        int tempWidth;
        String lineStr;
        int returnInt;
        if (lineNum == 0) {
            returnStr = str;
            mHeight += LINE_HEIGHT;
            return returnStr;
        } else {
            //一行大概有多少个字
            int oneLine = len / (lineNum + 1);
            if (numberOfWords < oneLine) {
                numberOfWords = oneLine;
            }

            lineStr = str.substring(0, oneLine);
            tempWidth = (int) mPaint.measureText(lineStr);

            //如果小了 找到大的那个
            if (tempWidth < mViewWidth) {
                while (tempWidth < mViewWidth) {
                    oneLine++;
                    lineStr = str.substring(0, oneLine);
                    tempWidth = (int) mPaint.measureText(lineStr);
                }
                returnInt = oneLine - 1;
                returnStr = lineStr.substring(0, lineStr.length() - 2);
            } else//大于宽找到小的
            {
                while (tempWidth > mViewWidth) {
                    oneLine--;
                    lineStr = str.substring(0, oneLine);
                    tempWidth = (int) mPaint.measureText(lineStr);
                }
                returnStr = lineStr.substring(0, lineStr.length() - 1);
                returnInt = oneLine;
            }
            mHeight += LINE_HEIGHT;
            returnStr += "\n" + caculateOneLine(str.substring(returnInt - 1));
        }
        return returnStr;
    }

    public void caculateChangeLine() {
        newText = new StringBuffer();
        String[] tempStr = mText.toString().split("\n");
        for (String aTempStr : tempStr) {
            String caculateOneLine = caculateOneLine(aTempStr);
            if (!TextUtils.isEmpty(caculateOneLine)) {
                newText.append(caculateOneLine);
                newText.append("\n");
            }

        }
        this.setHeight(mHeight);
    }


    private void drawScaledText(Canvas canvas, int lineStart, String line,
                                float lineWidth) {
        float x = 0;
        // 判断是否是第一行
        if (isFirstLineOfParagraph(lineStart, line)) {
            canvas.drawText(TWO_CHINESE_BLANK, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(TWO_CHINESE_BLANK, getPaint());
            x += bw;

            line = line.substring(3);
        }
        int gapCount = line.length() - 1;
        int i = 0;
        if (line.length() > 2 && line.charAt(0) == 12288
                && line.charAt(1) == 12288) {
            String substring = line.substring(0, 2);
            float cw = StaticLayout.getDesiredWidth(substring, getPaint());
            canvas.drawText(substring, x, mLineY, getPaint());
            x += cw;
            i += 2;
        }

        float d = (mViewWidth - lineWidth) / gapCount;
        for (; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw + d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        return line.length() > 3 && line.charAt(0) == ' '
                && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {// 判断是否需要换行
        if (line == null || line.length() == 0) {
            return false;
        } else {
            char charAt = line.charAt(line.length() - 1);
            return charAt != '\n';
        }
    }
}
