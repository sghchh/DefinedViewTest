package com.example.as.definedviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MyCircleImageView imageView;
    private StateView state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.circle);
        state=(StateView)findViewById(R.id.state);
        state.initListener();
    }
}
