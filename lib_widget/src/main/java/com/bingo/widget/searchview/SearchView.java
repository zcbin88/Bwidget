package com.bingo.widget.searchview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingo.widget.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by liuyunming on 2016/7/6.
 */
public class SearchView extends LinearLayout{

    private  String msearch_hint;
    private  int msearch_baground;
    Context context;
    private ImageView ib_searchtext_delete;
    private EditText et_searchtext_search;
    private LinearLayout searchview;
    private Button bt_text_search_back;
    private ImageView ivclearHistory;
    ImageButton btnBack;
    private boolean showHistory;//是否显示历史记录
    LinearLayout historyLayout;


    //历史搜索
    private SearchGridView gridviewolddata;
    private SearchOldDataAdapter OldDataAdapter;
    private ArrayList<String> OldDataList =new ArrayList<String>();

    private String backtitle="取消",searchtitle="搜索";
    private OnClickListener TextViewItemListener;
    private int countOldDataSize=15;//默认搜索记录的条数， 正确的是传入进来的条数


    public SearchView(Context context) {
        super(context);
        this.context = context;
        InitView();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        msearch_hint = ta.getString(R.styleable.SearchView_search_view_hint);
        msearch_baground = ta.getResourceId(R.styleable.SearchView_search_background,R.drawable.search_bg_et);
        showHistory= ta.getBoolean(R.styleable.SearchView_show_history,false);
        ta.recycle();


        InitView();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        InitView();
    }




    private void  InitView(){
        backtitle=getResources().getString(R.string.search_cancel);
        searchtitle=getResources().getString(R.string.search_verify);

        searchview =(LinearLayout)LayoutInflater.from(context).inflate(R.layout.search_view, null);
        //把获得的view加载到这个控件中
        addView(searchview);
        //把两个按钮从布局文件中找到
        ib_searchtext_delete = searchview.findViewById(R.id.ib_searchtext_delete);
        et_searchtext_search = searchview.findViewById(R.id.et_searchtext_search);
        btnBack =  searchview.findViewById(R.id.btn_back);
        historyLayout = searchview.findViewById(R.id.history_layout);
        bt_text_search_back =  searchview.findViewById(R.id.buttonback);
        ivclearHistory = searchview.findViewById(R.id.iv_clear_history);
        gridviewolddata=  searchview.findViewById(R.id.gridviewolddata);

        historyLayout.setVisibility(showHistory?View.VISIBLE:View.GONE);

        et_searchtext_search.setBackgroundResource(msearch_baground);
        et_searchtext_search.setHint(msearch_hint);
        gridviewolddata.setSelector(new ColorDrawable(Color.TRANSPARENT));//去除背景点击效果

        setLinstener();
    }

    public boolean isShowHistory() {
        return showHistory;
    }

    public void setShowHistory(boolean show) {
        this.showHistory = show;
        historyLayout.setVisibility(show?View.VISIBLE:View.GONE);
    }

    public void setSearchHint(String hint){
        msearch_hint=hint;
        et_searchtext_search.setHint(hint);
        invalidate();
    }


    //文本观察者
    private class MyTextWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        //当文本改变时候的操作
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            //如果编辑框中文本的长度大于0就显示删除按钮否则不显示
            if(s.length() > 0){
                ib_searchtext_delete.setVisibility(View.VISIBLE);
                bt_text_search_back.setText(searchtitle);
            }else{
                ib_searchtext_delete.setVisibility(View.GONE);
                bt_text_search_back.setText(backtitle);
            }
        }

    }

    private void setLinstener() {

        //给删除按钮添加点击事件
        ib_searchtext_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_searchtext_search.setText("");
            }
        });
        //给编辑框添加文本改变事件
        et_searchtext_search.addTextChangedListener(new MyTextWatcher());


        et_searchtext_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    String searchtext = et_searchtext_search.getText().toString().trim();
                    //dosoming
//                    Toast.makeText(context, "搜索" +searchtext, Toast.LENGTH_SHORT).show();

                    executeSearch_and_NotifyDataSetChanged(searchtext);

                    return true;
                }
                return false;
            }
        });



        bt_text_search_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext = et_searchtext_search.getText().toString().trim();
                if (bt_text_search_back.getText().toString().equals(searchtitle)) {
//                    Toast.makeText(context, "点击button搜索" + searchtext, Toast.LENGTH_SHORT).show();
                        executeSearch_and_NotifyDataSetChanged(searchtext);
                } else {
//                    Toast.makeText(context, "点击button  返回", Toast.LENGTH_SHORT).show();
                    if (sCBlistener != null)
                        sCBlistener.Back();
                }
            }
        });

        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sCBlistener != null){
                    sCBlistener.Back();
                }
            }
        });



        ivclearHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sCBlistener!=null) {
                    OldDataList.clear();
                    OldDataAdapter.notifyDataSetChanged();
                    sCBlistener.ClearOldData();
                }
            }
        });




        TextViewItemListener = new OnClickListener(){
            @Override
            public void onClick(View v) {
                String string = ((TextView)v).getText().toString();

//                Toast.makeText(context, "Item点击"+string, Toast.LENGTH_SHORT).show();

                executeSearch_and_NotifyDataSetChanged(string);

            }
        };



        gridviewolddata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context, "历史记录点击"+OldDataList.get(position), Toast.LENGTH_SHORT).show();
                if(sCBlistener!=null){
                    sCBlistener.Search(OldDataList.get(position).trim());
                }
            }
        });

    }


    /**
     *
     * @param olddatalist  历史搜索数据集合
     * @param sCb  事件处理监听
     */
    public void initData(List<String> olddatalist,setSearchCallBackListener sCb){

        SetCallBackListener(sCb);

        OldDataList.clear();
        if(olddatalist!=null)
        OldDataList.addAll(olddatalist);


        OldDataAdapter = new SearchOldDataAdapter(context,OldDataList);
        gridviewolddata.setAdapter(OldDataAdapter);

    }



    private void executeSearch_and_NotifyDataSetChanged(String str){
        if(sCBlistener!=null&&(!str.equals(""))){
            if (OldDataList.size() > 0 && OldDataList.get(0).equals(str)) {
            }
            else
            {
                if (OldDataList.size() == countOldDataSize&&OldDataList.size()>0)
                    OldDataList.remove(OldDataList.size() - 1);
                OldDataList.add(0, str);//把最新的添加到前面
                OldDataAdapter.notifyDataSetChanged();
                sCBlistener.SaveOldData(OldDataList);
            }
            sCBlistener.Search(str);
        }
    }




    /**
     * 生成随机数
     * @param max  最大值
     * @param min   最小值
     * @return
     */
    public int MyRandom(int min,int max){

        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }


    //对外开发的接口
//--------------------------------------------------------------------------------------

    /**
     * @author liuyunming
     */
    public interface setSearchCallBackListener{

        /**
         * @param str  搜索关键字
         */
        public void Search(String str);

        /**
         * 后退
         */
        public abstract void Back();

        /**
         * 清除历史搜索记录
         */
        public abstract void ClearOldData();

        /**
         * 保存搜索记录
         */
        public abstract void SaveOldData(ArrayList<String> AlloldDataList);

    }

    private setSearchCallBackListener sCBlistener;
    /**
     * 设置接口回调
     * @param sCb   setCallBackListener接口
     */
    private void SetCallBackListener(setSearchCallBackListener sCb){
        sCBlistener=sCb;
    }

}
