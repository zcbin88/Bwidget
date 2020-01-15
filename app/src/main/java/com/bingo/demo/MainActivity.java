package com.bingo.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bingo.demo.activity.GridCommonActivity;
import com.bingo.demo.activity.ImageActivity;
import com.bingo.demo.activity.InputViewActivity;
import com.bingo.demo.activity.SignActivity;
import com.bingo.demo.adapter.MemberPrivilegeAdapter;
import com.bingo.demo.bean.MenuEntity;
import com.bingo.widget.LineGridView;
import com.bingo.widget.gridview.BaseGridViewAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BaseGridViewAdapter.OnItemClickListener{
    LineGridView myGridView;
    MemberPrivilegeAdapter gridAdapter;
    ArrayList<MenuEntity> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myGridView= findViewById(R.id.my_grid_view);
        data = new ArrayList();
        MenuEntity gridAty =new MenuEntity("GridActivity",R.drawable.icon_down);
        data.add(gridAty);
        MenuEntity stickAty =new MenuEntity("StickActivity",R.drawable.icon_down);
        data.add(stickAty);
        MenuEntity inputViewAty =new MenuEntity("InputViewAty",R.drawable.icon_down);
        data.add(inputViewAty);
        //查看图片 activity
        MenuEntity imgAty =new MenuEntity("ImgAty",R.drawable.icon_down);
        data.add(imgAty);
        MenuEntity signAty =new MenuEntity("SignAty",R.drawable.icon_down);
        data.add(signAty);

        gridAdapter = new MemberPrivilegeAdapter(data,R.layout.item_member_privilege);
        myGridView.setAdapter(gridAdapter);

        gridAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseGridViewAdapter adapter, View view, int position) {
        switch (position){
            case 0:
                startActivity(new Intent(this, GridCommonActivity.class));
                break;
            case 1:
                Toast.makeText(this,"Stick Activity",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                startActivity(new Intent(this, InputViewActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, ImageActivity.class));
                break;
                case 4:
                startActivity(new Intent(this, SignActivity.class));
                break;
                default:
                    break;
        }
    }
}
