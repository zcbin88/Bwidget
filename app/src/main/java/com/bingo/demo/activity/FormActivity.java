package com.bingo.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.bingo.demo.R;
import com.bingo.widget.BRadioView;
import com.bingo.widget.BTabView;
import com.bingo.widget.model.ItemBean;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
 /**
  * ================================
  * @author:  zcb
  * @email:   zhang-cb@foxmail.com
  * @time:    2020-01-15 09:42
  * @version: 1.0
  * @description: Form表单常用控件整理
  * =================================
  */
public class FormActivity extends AppCompatActivity {

    BRadioView customRadioView ;
    BTabView bTabView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        customRadioView = findViewById(com.bingo.widget.R.id.custom_radio_view);
        bTabView = findViewById(R.id.custom_tab_view);

        initRadioView();
        initTabView();
    }


    private void initRadioView(){
        List<BRadioView.ItemBean> list= new ArrayList();
        list.add(new BRadioView.ItemBean("sparepart_code","Code",true));
        list.add(new BRadioView.ItemBean("location","Location",false));
        list.add(new BRadioView.ItemBean("sparepart_des","Description",false));
        customRadioView.setDatas(list);
        customRadioView.setOnItemClickListener(new BRadioView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, BRadioView.ItemBean item) {
                Toast.makeText(FormActivity.this,"customRadioView",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initTabView(){
        List<ItemBean> tabs = new ArrayList<>();
        tabs.add(new ItemBean("code",R.drawable.icon_down,true));
        tabs.add(new ItemBean("price",R.drawable.icon_down,false));
        tabs.add(new ItemBean("name",R.drawable.icon_down,false));
        bTabView.setDatas(tabs);
    }
}
