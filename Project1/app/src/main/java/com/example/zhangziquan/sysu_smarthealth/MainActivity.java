package com.example.zhangziquan.sysu_smarthealth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends Activity  {
    Boolean tag = false;
    List<Collection> recipe_list;
    List<Collection> favorite_list;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initfoodlist();
        initfavorite();
    }

    public static List<Collection> readFileByLines(InputStream file) {
        BufferedReader reader = null;
        List<Collection> data = new ArrayList<>();
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new InputStreamReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                System.out.println("line " + line + ": " + tempString);
                line++;
                if (line == 2) {
                    continue;
                }
                String[] ss = new String[20];
                String recipeString = tempString;
                ss = recipeString.split("\t+");
                Collection recipe = new Collection(ss[0],ss[2],ss[3],ss[4]);
                data.add(recipe);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void click_fab(View view){
        RecyclerView food_list = (RecyclerView) findViewById(R.id.food_list);
        ListView favorites = (ListView) findViewById(R.id.favorites);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view;
        if(tag == false) {
            floatingActionButton.setImageResource(R.drawable.mainpage);
            food_list.setVisibility(View.INVISIBLE);
            favorites.setVisibility(View.VISIBLE);
            tag = true;
        }
        else {
            floatingActionButton.setImageResource(R.drawable.collect);
            food_list.setVisibility(View.VISIBLE);
            favorites.setVisibility(View.INVISIBLE);
            tag = false;
        }
    }

    public void initfoodlist(){
        RecyclerView food_list = (RecyclerView) findViewById(R.id.food_list);
        recipe_list = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.food_info);
        recipe_list = readFileByLines(inputStream);
        final MyRecyclerViewAdapter myAdapter = new MyRecyclerViewAdapter<Collection>(MainActivity.this, R.layout.item, recipe_list) {

            @Override
            public void convert(MyViewHolder holder, Collection s) {
                // Colloction是自定义的一个类，封装了数据信息，也可以直接将数据做成一个Map，那么这里就是Map<String, Object>
                TextView name = holder.getView(R.id.recipeName);
                name.setText(s.getName().toString());
                Button first = holder.getView(R.id.img);
                first.setText(s.getFirst().toString());

            }
        };
        food_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        food_list.setAdapter(myAdapter);

        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(myAdapter);
        scaleInAnimationAdapter.setDuration(1000);
        food_list.setAdapter((scaleInAnimationAdapter));
        food_list.setItemAnimator(new OvershootInLeftAnimator());

        myAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener(){

            @Override
            public void onClick(int position) {
                Collection food = (Collection) myAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe",food);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongClick(int position) {
                Collection food = (Collection) myAdapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        food.getName() + "删除", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initfavorite(){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        favorite_list = new ArrayList<>();
        favorite_list.add(new Collection("收藏夹","*","wood","#000000"));
        final MyListViewAdapter myListViewAdapter = new MyListViewAdapter(MainActivity.this, favorite_list);
        favorites.setAdapter(myListViewAdapter);


        favorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    return;
                }
                Collection food = (Collection) myListViewAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe",food);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });

        favorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(position == 0)
                {
                    return true;
                }
                final Collection food = favorite_list.get(position);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("删除").setMessage("确定删除" + food.getName() + "?").setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                favorite_list.remove(position);
                                myListViewAdapter.refresh(favorite_list);
                                Toast.makeText(getApplicationContext(),
                                        food.getName() + "已删除", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),
                                        "取消删除", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alertDialog.show();
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 2){
            if(requestCode == 1){
                Bundle bundle = data.getExtras();
                Collection recipe = (Collection) bundle.getSerializable("recipe");
                ListView favorites = (ListView) findViewById(R.id.favorites);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_list);
                MyListViewAdapter myListViewAdapter =(MyListViewAdapter) favorites.getAdapter();
                ScaleInAnimationAdapter scaleInAnimationAdapter =(ScaleInAnimationAdapter) recyclerView.getAdapter();
                MyRecyclerViewAdapter myRecyclerViewAdapter = (MyRecyclerViewAdapter) scaleInAnimationAdapter.getWrappedAdapter();
                if (recipe.isCollected())
                {
                    favorite_list.add(recipe);
                }
                for (Collection food : favorite_list){
                    if (food.getName().equals(recipe.getName())){
                        food.setIsFavor(recipe.getFavor());
                    }
                }
                for (Collection food : recipe_list){
                    if (food.getName().equals(recipe.getName())){
                        food.setIsFavor(recipe.getFavor());
                    }
                }
                myListViewAdapter.refresh(favorite_list);
                myRecyclerViewAdapter.refresh(recipe_list);
            }
        }
    }
}