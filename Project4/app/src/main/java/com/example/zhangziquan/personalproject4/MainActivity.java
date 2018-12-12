package com.example.zhangziquan.personalproject4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_CODE = 0;
    private static final int STOP_CODE = 1;
    private static final int SETSOURCE_CODE = 2;
    private static final int RESET_CODE = 3;
    private static final int SEEK_CODE = 4;
    private static final int POS_CODE = 5;

    private ImageView btn_play;
    private ImageView btn_stop;
    private ImageView btn_exit;
    private ImageView btn_file;
    private SeekBar sb_playbar;
    private TextView tv_cp;
    private TextView tv_dr;
    private IBinder ms;
    private Runnable Imagerun;
    private String path = "/sdcard/Download/山高水长.mp3";

    private TextView tv_title;
    private TextView tv_artist;

    private boolean isPlaying = false;
    private boolean isStoping = false;

    private boolean isBinded = false;
    private CircleImageView civ_mc;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");

    private ServiceConnection sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                10000
        );

        btn_play = findViewById(R.id.btn_play);
        btn_exit = findViewById(R.id.btn_exit);
        btn_file = findViewById(R.id.btn_file);
        btn_stop = findViewById(R.id.btn_stop);
        tv_cp = findViewById(R.id.tv_cp);
        tv_dr = findViewById(R.id.tv_dr);
        civ_mc = findViewById(R.id.civ_mc);

        tv_title = findViewById(R.id.tv_title);
        tv_artist = findViewById(R.id.tv_artist);

        sb_playbar = findViewById(R.id.seekBar);
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                ms = service;
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    ms.transact(POS_CODE,data,reply,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                int[] pos = new int[2];
                reply.readIntArray(pos);
                String cp = time.format(pos[0]);
                String dr = time.format(pos[1]);
                tv_cp.setText(cp);
                tv_dr.setText(dr);
                sb_playbar.setProgress(pos[0]);
                sb_playbar.setMax(pos[1]);

                imageRun();
                musicplay();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        if(!isBinded){
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, sc, BIND_AUTO_CREATE);
            isBinded = true;
        }

        sb_playbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    data.writeInt(progress);
                    try {
                        ms.transact(SEEK_CODE,data,reply,0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
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

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        tv_title.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        tv_artist.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        byte[] imagedata = mmr.getEmbeddedPicture();
        if(imagedata!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
            civ_mc.setImageBitmap(bitmap);
        }
        mmr.release();
    }

    private void imageRun(){
        rx.Observable imageobservable = rx.Observable.create(new rx.Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                Subscriber subscriber = (Subscriber)o;
                while(true){
                    try {
                        subscriber.onNext(o);
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        imageobservable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if(isPlaying) {
                    civ_mc.setRotation(civ_mc.getRotation()+(float)0.5);
                }else if(isStoping){
                    civ_mc.setRotation(0);
                    btn_play.setImageResource(R.drawable.play);
                }
            }
        });
    }

    private void musicplay(){
        rx.Observable observable = rx.Observable.create(new rx.Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                Subscriber subscriber = (Subscriber)o;
                while(true){
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    try {
                        ms.transact(POS_CODE,data,reply,0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    int[] pos = new int[2];
                    reply.readIntArray(pos);
                    subscriber.onNext(pos);
                    if(reply.readInt() == 1){
                        isPlaying = false;
                        isStoping = true;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                int[] pos = (int[]) o;
                String cp = time.format(pos[0]);
                String dr = time.format(pos[1]);
                tv_cp.setText(cp);
                tv_dr.setText(dr);
                sb_playbar.setProgress(pos[0]);
                sb_playbar.setMax(pos[1]);
            }
        });
    }

    public void play(View view){
        try {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            ms.transact(PLAY_CODE,data,reply,0);
            if(reply.readString().equals("PLAY")) {
                isPlaying = true;
                isStoping = false;
                btn_play.setImageResource(R.drawable.pause);
            }else{
                isPlaying = false;
                btn_play.setImageResource(R.drawable.play);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stop(View view){
        Parcel pdata = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            ms.transact(STOP_CODE,pdata,reply,0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        isPlaying = false;
        isStoping = true;
        String cp = time.format(0);
        tv_cp.setText(cp);
        civ_mc.setRotation(0);
        sb_playbar.setProgress(0);
        btn_play.setImageResource(R.drawable.play);
    }

    public void exit(View view){
        isPlaying = false;
        unbindService(sc);
        try {
            MainActivity.this.finish();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readfile(View view){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
//        super.onBackPressed(); //注释super,拦截返回键功能
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();
                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
            }
            try {
                Parcel pdata = Parcel.obtain();
                pdata.writeString(path);
                Parcel reply = Parcel.obtain();
                isPlaying = false;
                isStoping = true;
                ms.transact(SETSOURCE_CODE,pdata,reply,0);
                civ_mc.setRotation(0);
                btn_play.setImageResource(R.drawable.play);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(path);
                tv_title.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
                tv_artist.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                byte[] imagedata = mmr.getEmbeddedPicture();
                if(imagedata!=null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
                    civ_mc.setImageBitmap(bitmap);
                }else{
                    civ_mc.setImageResource(R.drawable.img);
                }
                mmr.release();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }



    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
