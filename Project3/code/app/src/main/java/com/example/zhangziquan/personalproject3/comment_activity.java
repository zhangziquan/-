package com.example.zhangziquan.personalproject3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class comment_activity extends Activity {

    Integer id;
    String username;
    List<Comment> commentList;
    private EditText new_comment;
    private ListView comments;
    private myDB myDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        init();
    }

    public void init(){
        myDB = new myDB(this);
        comments = (ListView) findViewById(R.id.comments);
        new_comment = (EditText) findViewById(R.id.new_comment);
        Intent intent = getIntent();
        id = intent.getIntExtra("userid",-1);
        username = intent.getStringExtra("username");
        commentList = new ArrayList<>();
        load_comment();
        final MyListViewAdapter myListViewAdapter = new MyListViewAdapter(comment_activity.this,commentList,id);
        myListViewAdapter.setOnItemLikeClickListener(new MyListViewAdapter.onItemLikeListener() {
            @Override
            public boolean onLikeClick(int i) {
                boolean islike = commentList.get(i).likeordislike(id);
                myListViewAdapter.refresh(commentList);
                myDB.updateComment(commentList.get(i).get_id(),commentList.get(i).getLike_num(),list2String(commentList.get(i).getUserlike()));
                return islike;
            }
        });
        comments.setAdapter(myListViewAdapter);
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        id = data.getIntExtra("userid",-1);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void send_comment(View view){
        String newcomment = new_comment.getText().toString();
        if(newcomment.equals("")) {
            Toast.makeText(getApplicationContext(),"comment cannot be empty",Toast.LENGTH_SHORT).show();
        }else{
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date = sDateFormat.format(new java.util.Date());
            int id = myDB.insertComment(username,date,newcomment,0,"");
            commentList.add(new Comment(id,getAvatar(username),username,date,0,newcomment,new ArrayList<>()));
            MyListViewAdapter myListViewAdapter =(MyListViewAdapter) comments.getAdapter();
            myListViewAdapter.refresh(commentList);
        }
        new_comment.setText("");
    }

    public void load_comment(){
        Cursor cursor = myDB.selectAllComment();
        if(cursor.getCount()!=0)
        {
            while (cursor.moveToNext()){
                String username = cursor.getString(cursor.getColumnIndex("uname"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String likelist =cursor.getString(cursor.getColumnIndex("likelist"));
                int likes = cursor.getInt(cursor.getColumnIndex("likes"));
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                Drawable avatar = getAvatar(username);
                Comment comment = new Comment(id,avatar,username,time,likes,content,String2List(likelist));
                commentList.add(comment);
            }
        }
    }

    public Drawable getAvatar(String username)
    {
        User user = myDB.selectUser(username);
        if(user == null) {
            return null;
        }else{
            Bitmap bmpout = BitmapFactory.decodeByteArray(user.getAvatar(), 0, user.getAvatar().length);
            BitmapDrawable bd= new BitmapDrawable(getResources(), bmpout);
            return bd;
        }
    }

    public void setListener(){
        comments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String username = commentList.get(position).getUsername();
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(comment_activity.this);

                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + username + "\"", null, null);
                String number = "\nPhone: ";
                if(cursor.getCount()!=0){
                    cursor.moveToFirst();
                    do {
                        number += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "\n";
                    } while (cursor.moveToNext());
                }else{
                    number = "\nPhone number not exist.";
                }

                alertDialog.setTitle("Info").setMessage("Username: "+username + number).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                cursor.close();
            }
        });

        comments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String c_username = commentList.get(position).getUsername();
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(comment_activity.this);
                if(c_username.equals(username))
                {
                    alertDialog.setMessage("Delete or not?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDB.deleteComment(commentList.get(position).get_id());
                            commentList.remove(position);
                            MyListViewAdapter myListViewAdapter =(MyListViewAdapter) comments.getAdapter();
                            myListViewAdapter.refresh(commentList);
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }else{
                    alertDialog.setMessage("Report or not?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"reported",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
                return true;
            }
        });
    }

    public String list2String(List<Integer>list){
        String output="";
        for(int i =0;i<list.size();i++){
            output+=Integer.toString(list.get(i))+",";
        }
        return output;
    }
    public List<Integer> String2List(String s){
        List<Integer> list = new ArrayList<>();
        String[] split = s.split(",");
        for(int i = 0; i<split.length;i++){
            if(split[i]=="")
                continue;
            list.add(Integer.parseInt(split[i]));
        }
        return list;
    }
}
