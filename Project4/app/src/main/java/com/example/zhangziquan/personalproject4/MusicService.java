package com.example.zhangziquan.personalproject4;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

public class MusicService extends Service {
    public MediaPlayer mediaPlayer;

    public final IBinder binder = new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public int compeleted;

    public MusicService(){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("/sdcard/Download/山高水长.mp3");
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    compeleted = 1;
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.seekTo(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        private static final int PLAY_CODE = 0;
        private static final int STOP_CODE = 1;
        private static final int SETSOURCE_CODE = 2;
        private static final int RESET_CODE = 3;
        private static final int SEEK_CODE = 4;
        private static final int POS_CODE = 5;

        //        MusicService getService() {
//            return MusicService.this;
//        }
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code){
                case PLAY_CODE:
                    if(play()){
                        reply.writeString("PLAY");
                    }else {
                        reply.writeString("PAUSE");
                    }
                    break;
                case STOP_CODE:
                    stop();
                    break;
                case SETSOURCE_CODE:
                    mediaPlayer.reset();
                    try {
                        mediaPlayer.setDataSource(data.readString());
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case RESET_CODE:
                    break;
                case SEEK_CODE:
                    mediaPlayer.seekTo(data.readInt());
                    break;
                case POS_CODE:
                    int[] pos = new int[2];
                    pos[0] = mediaPlayer.getCurrentPosition();
                    pos[1] = mediaPlayer.getDuration();
                    reply.writeIntArray(pos);
                    reply.writeInt(compeleted);
                    break;
            }
            return super.onTransact(code,data,reply,flags);
        }
    }

    public boolean play(){
        compeleted = 0;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            return false;
        } else {
            mediaPlayer.start();
            return true;
        }
    }

    public void stop(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void seekTo(int position){
        mediaPlayer.seekTo(position);
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
