package com.example.zhangziquan.sysu_smarthealth;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

public class StaticReceiver extends BroadcastReceiver {

//    private static final int PUSH_NOTIFICATION_ID = (0x001);
//    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
//    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";

    private static final String STATICACTION = "com.example.zhangziquan.sysu_smarthealth.MyStaticFilter";
    private static final String WIDGETSTATICACTION = "com.example.zhangziquan.sysu_smarthealth.MyWidgetStaticFilter";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATICACTION)){
            Bundle bundle = intent.getExtras();
            //TODO:添加Notification部分

            Intent perIntent = new Intent(context,DetailActivity.class);
            perIntent.putExtras(bundle);

            perIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,perIntent,PendingIntent.FLAG_CANCEL_CURRENT);

            Notification.Builder builder = new Notification.Builder(context);
            //对Builder进行配置，此处仅选取了几个
            builder.setContentTitle("今日推荐")   //设置通知栏标题：发件人
                    .setContentText(bundle.getString("name"))   //设置通知栏显示内容：短信内容
                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
                    .setSmallIcon(R.mipmap.empty_star)   //设置通知小ICON（通知栏），可以用以前的素材，例如空星
                    .setFullScreenIntent(pendingIntent,true)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);   //传递内容

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);





//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID,PUSH_CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
//                if(notificationManager!=null){
//                    notificationManager.createNotificationChannel(channel);
//                }
//            }
//
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//            builder.setContentTitle("今日推荐")   //设置通知栏标题：发件人
//                    .setContentText(bundle.getString("name"))   //设置通知栏显示内容：短信内容
//                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
//                    .setSmallIcon(R.drawable.empty_star,1)   //设置通知小ICON（通知栏），可以用以前的素材，例如空星
//                    .setChannelId(PUSH_CHANNEL_ID)
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setWhen(System.currentTimeMillis())
//                    .setShowWhen(true)
//                    .setContentIntent(pendingIntent);   //传递内容
//
//            Notification notification = builder.build();
//            if(notificationManager!=null){
//                notificationManager.notify(PUSH_NOTIFICATION_ID,notification);
//            }
        }
    }
}