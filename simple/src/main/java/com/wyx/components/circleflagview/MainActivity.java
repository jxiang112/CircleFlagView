package com.wyx.components.circleflagview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wyx.components.circleflagview.core.CircleFlagView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleFlagView flagView = findViewById(R.id.flagview);
        flagView//.setNumber(100000000)
                .setBgColor(Color.BLUE)
                .setMaxShowNumber(77)
                .setNumber(100)
                .setTextType(CircleFlagView.TEXT_TYPE_NUMBER)
                .setOverWidthTextSize(24)
                .refreshView();
    }
}
