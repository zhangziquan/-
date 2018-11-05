package com.example.zhangziquan.sysu_smarthealth;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;

public class SmartHealth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_smart_health);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                String checkText = radioButton.getText().toString();
                Toast.makeText(getApplicationContext(),
                        checkText+"被选中", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        String content = editText.getText().toString();
        String success;
        if (content.isEmpty())
        {
            Toast.makeText(getApplication(), R.string.click_empty, Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(content.equals("HealthFoodList"))
            {
                //finish();
                Intent intent = new Intent();
                intent.setClass(SmartHealth.this,MainActivity.class);
                startActivity(intent);
                return;
            }
            if (content.equals("Health")) {
                success = "搜索成功";
            }
            else {
                success = "搜索失败";
            }
            RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            String checkText = radioButton.getText().toString();
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("提示").setMessage(checkText+success).setPositiveButton("确认",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),
                                    "对话框“确定”按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),
                                    "对话框“取消”按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
            alertDialog.show();
        }
    }
}
