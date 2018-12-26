package com.example.zhangziquan.personalproject5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enter_bilibili(View view){
        Intent intent = new Intent(MainActivity.this, BilibiliApi.class);
        startActivity(intent);
    }

    public void enter_gayhub(View view){
        Intent intent = new Intent(MainActivity.this,GayhubApi.class);
        startActivity(intent);
    }
}
