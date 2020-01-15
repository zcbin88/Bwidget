package com.bingo.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bingo.demo.R;
import com.bingo.widget.BInputView;

public class InputViewActivity extends AppCompatActivity {

    BInputView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_view);
        textView = findViewById(R.id.tv1);
    }

    public void date(View view) {
        textView.setShape(BInputView.SHAPE_DATE);
    }

    public void select(View view) {
        textView.setShape(BInputView.SHAPE_SELECT);
    }

    public void text(View view) {
        textView.setShape(BInputView.SHAPE_TEXT);
    }

    public void right(View view) {
        textView.setShape(BInputView.SHAPE_RIGHT);
    }

    public void edit(View view) {
        textView.setShape(BInputView.SHAPE_EDIT);
    }

    public void norequied(View view) {
        textView.setRequired(false);
    }

    public void yesrequied(View view) {
        textView.setRequired(true);
    }

    public void addListener(View view) {
        textView.addGvListener(new BInputView.OnGvListener() {
            @Override
            public void click() {
                Toast.makeText(InputViewActivity.this, "click event", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeListener(View view) {
        textView.removeGvListener();
    }
}
