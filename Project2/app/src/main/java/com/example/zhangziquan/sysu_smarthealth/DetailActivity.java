package com.example.zhangziquan.sysu_smarthealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

public class DetailActivity extends Activity {
    private static final String DYNAMICACTION = "com.example.zhangziquan.sysu_smarthealth.MyDynamicFilter";
    private static final String WIDGETDYNAMICACTION = "com.example.zhangziquan.sysu_smarthealth.WIDGETDYNAMICACTION";
    private DynamicReceiver dynamicReceiver;
    private DynamicReceiver widgetDynamicReceiver;

    Collection recipe;
    boolean favorchanged = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        TextView recipe_name = (TextView) findViewById(R.id.recipe_name);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        ImageButton star = (ImageButton) findViewById(R.id.star);
        TextView recipe_type = (TextView) findViewById(R.id.type);
        TextView nutrients = (TextView) findViewById(R.id.nutrients);
        LinearLayout top = (LinearLayout) findViewById(R.id.top);
        TextView moredetail = (TextView) findViewById(R.id.moredetail);

        Intent intent = getIntent();
        Serializable extras = intent.getSerializableExtra("recipe");
        if(extras!=null) {
            recipe = (Collection) extras;

            recipe_name.setText(recipe.getName());
            recipe_type.setText(recipe.getFood_type());
            nutrients.setText("富含" + recipe.getFood_nutrients());
            top.setBackgroundColor(Color.parseColor(recipe.getBg_color()));
            recipe.setIsCollected(false);

            moredetail.setText("更多资料");
            ListView operations = (ListView) findViewById(R.id.operation);
            String[] operate = {"分享信息", "不感兴趣", "查看更多信息", "出错反馈"};
            ArrayAdapter<String> operation = new ArrayAdapter<String>(DetailActivity.this,R.layout.operation, R.id.operate,operate);
            operations.setAdapter(operation);

            boolean favor = recipe.getFavor();
            if (favor == true){
                star.setImageResource(R.drawable.full_star);
            }else{
                star.setImageResource(R.drawable.empty_star);
            }
        }
        //注册动态广播
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(DYNAMICACTION);    //添加动态广播的Action
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, dynamic_filter);    //注册自定义动态广播消息

        //注册并发送动态广播
        IntentFilter widget_dynamic_filter = new IntentFilter();
        widget_dynamic_filter.addAction(WIDGETDYNAMICACTION);
        widgetDynamicReceiver = new DynamicReceiver(); //添加动态广播的Action
        registerReceiver(widgetDynamicReceiver, widget_dynamic_filter); //注册自定义动态广播信息
    }

    public void collect(View view){
        recipe.setIsCollected(true);
        Toast.makeText(getApplicationContext(),
                 "已收藏", Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putString("name",recipe.getName());
        Intent intentBroadcast = new Intent();   //定义Intent
        intentBroadcast.setAction(DYNAMICACTION);
        intentBroadcast.putExtras(bundle);
        sendBroadcast(intentBroadcast);

        //传递事件
        EventBus.getDefault().post(new MessageEvent(recipe));

        //发送widget动态广播
        Intent widgetIntentBroadcast = new Intent();   //定义Intent
        widgetIntentBroadcast.setAction(WIDGETDYNAMICACTION);
        widgetIntentBroadcast.putExtras(bundle);
        sendBroadcast(widgetIntentBroadcast);

    }

    public void goback (View view){
        finish();
    }

    public void onBackPressed(){
        finish();
    }

    public void favor (View view){
        ImageButton star = (ImageButton) findViewById(R.id.star);
        boolean favor = recipe.getFavor();
        if (favor == false){
            star.setImageResource(R.drawable.full_star);
        }else{
            star.setImageResource(R.drawable.empty_star);
        }
        favorchanged = true;
        favor = !favor;
        recipe.setIsFavor(favor);
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
        unregisterReceiver(widgetDynamicReceiver);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(dynamicReceiver);
//    }

    public static class MessageEvent {
        private Collection recipe;
        public MessageEvent(Collection recipe){
            this.recipe = recipe;
        }

        public Collection getMessage(){
            return recipe;
        }

        public void setMessage(Collection recipe) {
            this.recipe = recipe;
        }
    }
}
