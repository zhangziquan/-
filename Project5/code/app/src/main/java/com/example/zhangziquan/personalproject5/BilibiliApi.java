package com.example.zhangziquan.personalproject5;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

public class BilibiliApi extends AppCompatActivity {
    EditText ed_id;
    RecyclerView rv_list;
    MyRecyclerViewAdapter myAdapter;

    List<Video.Data> videoList;

    String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilibili);

        List <String>mPermissionList = new ArrayList();
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);
            }
        }

        for (int i = 0; i < mPermissionList.size(); i++) {
            ActivityCompat.requestPermissions(BilibiliApi.this, new String[]{mPermissionList.get(i)}, i);
        }

        init();
    }

    private void init() {
        videoList = new ArrayList<>();
        ed_id = findViewById(R.id.et_id);
        rv_list = findViewById(R.id.rv_video);

        myAdapter = new MyRecyclerViewAdapter<Video.Data>(BilibiliApi.this, R.layout.item, videoList) {

            @Override
            public void convert(MyViewHolder holder, final Video.Data s) {
                // 绑定视频信息
                TextView tv_play = holder.getView(R.id.tv_play);
                tv_play.setText("播放: " + s.getPlay().toString() + "  评论: " + s.getVideo_review() + "  时长 " + s.getDuration());
                TextView tv_create = holder.getView(R.id.tv_create);
                tv_create.setText("创建时间: " + s.getCreate());
                TextView tv_title = holder.getView(R.id.tv_title);
                tv_title.setText(s.getTitle());
                TextView tv_content = holder.getView(R.id.tv_content);
                tv_content.setText(s.getContent());
                final ImageView iv_cover = holder.getView(R.id.iv_cover);
                final ProgressBar pb_img = holder.getView(R.id.pb_img);
                pb_img.bringToFront();

                final SeekBar sb_sprite = holder.getView(R.id.seekBar);
                sb_sprite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser){
                            Integer num =0;
                            Integer spritenum = 0;
                            Integer [] index = s.getIndex();
                            Bitmap[] sprites = s.getSpiritimage();
                            for (int i = 0; i<index.length;i++){
                                if (index[i]>=progress){
                                    num = i;
                                    break;
                                }
                            }
                            spritenum = num/99;
                            num = num % 100;
                            Bitmap sprite = Bitmap.createBitmap(sprites[spritenum],(num%10)*160 ,(num/10)*90 , 160,90);
                            iv_cover.setImageBitmap(sprite);

                            if(progress == 0){
                                iv_cover.setImageBitmap(s.getCoverimage());
                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                //通过url取得图片
                if(s.getCoverimage()==null) {
                    rx.Observable observable = Observable.create(new Observable.OnSubscribe() {

                        @Override
                        public void call(Object o) {
                            pb_img.setVisibility(View.VISIBLE);
                            Subscriber subscriber = (Subscriber) o;
                            Object[] output = new Object[3];
                            try {
                                URL cover = new URL(s.getCover());
                                HttpURLConnection httpURLConnection = (HttpURLConnection) cover.openConnection();
                                httpURLConnection.setRequestMethod("GET");
                                if (httpURLConnection.getResponseCode() == 200) {
                                    InputStream inputStream = httpURLConnection.getInputStream();
                                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    output[0] = bitmap;
                                }
                                httpURLConnection.disconnect();

                                URL url = new URL("https://api.bilibili.com/pvideo?aid=" + s.getAid());
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                Log.i("HTTP", "respCode = " +
                                        httpURLConnection.getResponseCode());
                                Log.i("HTTP", "contentType = " +
                                        httpURLConnection.getContentType());
                                Log.i("HTTP", "content = " + httpURLConnection.getContent());
                                InputStream is = conn.getInputStream();//获得输入流
                                String json = inputstream2String(is);

                                JSONArray spriteurl = null;
                                JSONArray index = null;
                                try {
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONObject data = (JSONObject) jsonObject.get("data");
                                    index = data.getJSONArray("index");
                                    spriteurl = data.getJSONArray("image");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Bitmap[] sprites = new Bitmap[spriteurl.length()];
                                for(int i = 0;i<spriteurl.length();i++){
                                    URL sprite = new URL(spriteurl.getString(i));
                                    httpURLConnection = (HttpURLConnection) sprite.openConnection();
                                    httpURLConnection.setRequestMethod("GET");
                                    if (httpURLConnection.getResponseCode() == 200) {
                                        InputStream inputStream = httpURLConnection.getInputStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                        if(bitmap == null){
                                            sprites = null;
                                            break;
                                        }
                                        sprites[i] = bitmap;
                                    }
                                    httpURLConnection.disconnect();
                                }
                                output[1] = sprites;
                                output[2] = index;

                                subscriber.onNext(output);

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            subscriber.onCompleted();
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                    observable.subscribe(new Subscriber() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Object o) {
                            Object[] out = (Object[]) o;
                            Bitmap cover = (Bitmap) out[0];
                            iv_cover.setImageBitmap(cover);
                            pb_img.setVisibility(View.INVISIBLE);

                            s.setCoverimage(cover);
                            s.setSpiritimage((Bitmap[]) out[1]);
                            if(s.getSpiritimage() == null){
                                sb_sprite.setEnabled(false);
                                sb_sprite.setMax(0);
                            }

                            JSONArray ja = (JSONArray) out[2];
                            Integer[] index = new Integer[ja.length()];
                            for (int i = 0; i < ja.length(); i++) {
                                index[i] = ja.optInt(i);
                            }
                            s.setIndex(index);
                            sb_sprite.setMax(index[ja.length() - 1]);
                        }
                    });
                }else {
                    iv_cover.setImageBitmap(s.getCoverimage());
                    pb_img.setVisibility(View.INVISIBLE);
                    if(s.getSpiritimage() == null){
                        sb_sprite.setEnabled(false);
                        sb_sprite.setMax(0);
                    }
                    sb_sprite.setMax(s.getIndex()[s.getIndex().length-1]);
                    sb_sprite.setProgress(0);
                }
            }
        };
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_list.setAdapter(myAdapter);
    }

    public void click_search(View view){

        rx.Observable observable = Observable.create(new Observable.OnSubscribe() {

            @Override
            public void call(Object o) {
                Subscriber subscriber = (Subscriber) o;
                String id = ed_id.getText().toString();
                if(!isInteger(id)){
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"请输入纯数字",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    subscriber.onCompleted();
                    return;
                }
                try {
                    URL url = new URL("https://space.bilibili.com/ajax/top/showTop?mid="+id);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    Log.i("HTTP", "respCode = " +
                            httpURLConnection.getResponseCode());
                    Log.i("HTTP", "contentType = " +
                            httpURLConnection.getContentType());
                    Log.i("HTTP", "content = " + httpURLConnection.getContent());
                    InputStream inputStream = httpURLConnection.getInputStream();//获得输入流
                    Video obj = new Gson().fromJson(inputstream2String(inputStream), Video.class);
                    subscriber.onNext(obj);
                    subscriber.onCompleted();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"网络连接失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    e.printStackTrace();
                } catch (Exception e){
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"数据库中不存在记录", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Video obj = (Video)o;
                if(obj.getStatus()){
                    videoList.add(obj.getData());
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static String inputstream2String(InputStream in){
        byte[] res;
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int length = 0;
            byte[] data = new byte[1024];
            while((length = in.read(data))!=-1){
                baos.write(data,0,length);
            }
            in.close();
            baos.close();
            res = baos.toByteArray();
            return new String (res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
