package com.bingo.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bingo.demo.R;
import com.bingo.demo.adapter.GridAdapter;
import com.bingo.demo.bean.MainItemBean;
import com.bingo.widget.gridview.CommonGridView;

import java.util.ArrayList;
import java.util.List;

/**
  * ================================
  * @author:  zcb
  * @email:   zhang-cb@foxmail.com
  * @time:    2019/12/5 16:23
  * @version: 1.0
  * @description:
  * =================================
  */
public class GridCommonActivity extends AppCompatActivity {
    CommonGridView commonGridView;
    GridAdapter mainAdapter;
    List<MainItemBean> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_common);
        data = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            data.add(new MainItemBean("Item"+i));
        }
        commonGridView = findViewById(R.id.my_grid_view);
        mainAdapter = new GridAdapter(data,this);
        commonGridView.setAdapter(mainAdapter);
    }
}
